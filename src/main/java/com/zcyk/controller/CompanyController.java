package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UnitprojectGis;
import com.zcyk.entity.*;
import com.zcyk.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 企业信息表(Company)表控制层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Slf4j
@RestController
@RequestMapping("company")
public class CompanyController {
    /**
     * 服务对象
     */
    @Resource
    private CompanyService companyService;

    @Resource
    private UserCompanyStationService userCompanyStationService;

    @Resource
    private CompanyCredentialService companyCredentialService;

    @Resource
    private UserCredentialService userCredentialService;

    @Resource
    private TradeService tradeService;

    @Resource
    private DistrictService districtService;

    @Resource
    HttpServletRequest request;

    @Resource
    UserService userService;


    /*
    * 1.单位加入密码怎么设置
    * 2.上级单位进入公司 身份是superior上级用户进入
    * 3.一个公司只能有一个上级
    * 4.子公司还能设下级子公司
    *
    *
    *
    *
    * */



    /**
     * 功能描述：获取企业加入密码
     * 开发人员： lyx
     * 创建时间： 2020/5/29 17:11
     * 参数： [pageNum, pageSie, company_id]
     * 返回值： com.github.pagehelper.PageInfo<com.zcyk.entity.CompanyRelation>
     * 异常：
     */
    @GetMapping(value = "/company_password/{company_id}")
    public String getCompanyJoinPassWord(@PathVariable String company_id){
        return companyService.getCompanyById(company_id).getCompany_join_password();
    }

    /**
     * 功能描述：获取用户加入密码
     * 开发人员： lyx
     * 创建时间： 2020/5/29 17:11
     * 参数： [pageNum, pageSie, company_id]
     * 返回值： com.github.pagehelper.PageInfo<com.zcyk.entity.CompanyRelation>
     * 异常：
     */
    @GetMapping(value = "/user_password/{company_id}")
    public String getUserJoinPassWord(@PathVariable String company_id){
        return companyService.getCompanyById(company_id).getUser_join_password();
    }


    /**
     * 功能描述： 根据编码查询 企业
     * 开发人员： lyx
     * 创建时间： 2020/5/25 9:25
     * 参数： [company]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     */
    @GetMapping("/{code}")
    public ResultData getCompanyByCode(@PathVariable String code) throws Exception {
        //1.判断该企业是否注册
        Company company = companyService.getByCode(code);

        if (company != null  ) {//有数据、未创建
            return ResultData.WRITE(200, "查询到企业", company);
        } else {//没有被注册，去天眼查寻这个公司信息 并写入数据库
            return  companyService.getBycodeForQCC(code);
        }
    }

    /**
     * 功能描述：模糊搜索本地企业
     * 开发人员： lyx
     * 创建时间： 2020/6/12 15:23
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping("/s/{search}")
    public ResultData searchCompany(@PathVariable String search) throws Exception {
        //1.判断该企业是否注册
        List<Company> companies = companyService.searchCompany(search);
        return ResultData.SUCCESS(companies);

    }




    /**
     * 功能描述：进入公司,返回用户信息
     * 开发人员： lyx
     * 创建时间： 2020/5/25 9:25
     * 参数： [c 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @GetMapping("/user/{company_id}")
    public Map<String,Object> enterCompany(@PathVariable String company_id)throws Exception{
        Map<String,Object> map = new HashMap<>();
        User nowUser = userService.getNowUser(request);
        UserCompanyStation userCompany = userCompanyStationService.getUserCompany(nowUser.getId(), company_id);

        Company company = companyService.getCompanyById(userCompany.getCompany_id());

        Trade_company tradeManager = tradeService.isTradeManager(company_id);
        District districtManager = districtService.isDistrictManager(company_id);


        if (tradeManager==null){
            map.put("trade_role",null);
        }else {
            String trade_code = tradeManager.getTrade_code();
            String area_code = trade_code.substring(0,5);
            map.put("trade_role",tradeManager.setTrade_code(area_code).setCompany_name(company.getName()).setCompany_logo(company.getLogo_url()));
        }

        if(districtManager==null){
            map.put("district_role",null);
        }else {
            map.put("district_role",districtManager.setCompany_name(company.getName()).setCompany_logo(company.getLogo_url()));
        }

        map.put("company_role",nowUser.setRole(userCompany.getRole()).setCompany_name(company.getName()).setCompany_logo(company.getLogo_url()).setCompany_id(company_id));

        return map;
    }

    /**
     * 功能描述：获取公司所有成员信息
     * 姓名 工号 性别 部门 岗位 副管 上级 委派
     * 开发人员： lyx
     * 创建时间： 2020/5/25 9:25
     * 参数： [company_id 公司id  status 0离职 1在职 2审核中=入职中]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @GetMapping("/users/{status}/{company_id}")
    public PageInfo<User> getCompanyUser(@PathVariable String company_id, @PathVariable Integer status,Integer pageSize,Integer pageNum,String search)throws Exception{
        PageInfo<User> pageInfo = userCompanyStationService.gatAllUser(company_id,pageSize,pageNum,search,status);
        return pageInfo;
    }





    /**
     * 功能描述：创建公司
     * 开发人员： lyx
     * 创建时间： 2020/5/25 9:25
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResultData addCompany(Company company)throws Exception{

        //根据编码查询该公司是不是被使用了
        Company byCode = companyService.getCompanyById(company.getId());
        if(null!=byCode && byCode.getStatus()==1){
            return ResultData.WRITE(400,"该公司已被注册");
        }
        if(null!=byCode && byCode.getStatus()==2){
            return ResultData.WRITE(400,"该公司已被注册,正在审核中");
        }
        if(null!=byCode && byCode.getStatus()==3){
            return ResultData.WRITE(400,"该公司已被冻结");
        }

        //一个人只能创建一个公司
        User nowUser = userService.getNowUser(request);
        List<Company> userNowCompanyStations =
                userCompanyStationService.getByUserId(nowUser.getId(), null, null).getList();

        //已经在公司
        int in = userNowCompanyStations.stream().filter(userCompanyStation -> userCompanyStation.getStatus() == 1 || userCompanyStation.getStatus() == 2)
                .collect(Collectors.toList()).size();

        if(in!=0){
            return ResultData.WRITE(400,"你已经在公司中（待审核），无法创建公司");
        }

        //添加公司
        if(companyService.addCompany(company.setStatus(2).setCreate_time(new Date()))){
            //将用户设置成企业管理员 并加入企业
            if(!userCompanyStationService.addUserToCompanyStation(new UserCompanyStation().setId(UUID.randomUUID().toString()).setStatus(1).setUser_id(nowUser.getId())
                    .setJoin_time(new Date()).setRole("admin").setCompany_id(company.getId()).setOperation_time(new Date()))){
                throw new Exception("添加公司 并委派人员失败");
            }

            return ResultData.WRITE(200,"成功添加公司");
        }

        return ResultData.FAILED();


    }


    /**
     * 功能描述：修改公司
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @PutMapping("/")
    public ResultData updateCompany(Company company)throws Exception{
        User nowUser = userService.getNowUser(request);
        UserCompanyStation userCompany = userCompanyStationService.getUserCompany(nowUser.getId(), company.getId());
        if("user".equals(userCompany.getRole())){
            return ResultData.WRITE(400,"不具备权限，请联系管理员");
        }
       return companyService.updateCompany(company);

    }







    /**
     * 功能描述： 添加企业，关联人员id，关联证书实体类
     * 开发人员： lyx
     * 创建时间： 2020/5/6 9:25
     * 参数： [company]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     *//*
    @PostMapping("updateCompany")
    @Transactional(rollbackFor = Exception.class)
    public ResultData addComany(@RequestBody @Valid Company company) throws Exception {


        String msg;
        if(company.getId()==null){
            msg = "添加";
        }else {
            msg = "修改";
        }
        String id ;

        try {
            //1、添加企业
            ResultData addCompany = companyService.updateCompany(company);
            if(addCompany.getCode()!=200){
                return addCompany;
            }
            id =(String) addCompany.getData();
            //添加人要设置成管理员？
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception(msg+"企业失败");
        }
        StringBuffer stringBuffer;
        try {
            //2、关联人员  一个人只能在一个企业
            stringBuffer = userCompanyService.updateUsers(company.getUsers(), id);
        }catch (Exception e){
            e.printStackTrace();

            throw new Exception(msg+"企业人员失败");
        }
        try {
            //3、关联证书 一个证书只能一个企业使用？？
            companyCredentialService.updateCredentials(company.getCompanyCredentials(),id);
        }catch (Exception e){
            e.printStackTrace();

            throw new Exception(msg+"企业证书失败");
        }

        return ResultData.WRITE(200, stringBuffer.length() > 13 ? stringBuffer.toString() : "操作成功");

    }

    *//**
     * 功能描述： 查询个人企业
     * 开发人员： lyx
     * 创建时间： 2020/5/6 9:25
     * 参数： [分页参数 搜索]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     *//*
    @PostMapping("getCompany")
    public List<Company> getUserCompany(HttpServletRequest request,
                                     @RequestParam(defaultValue = "1") int pageNum,
                                     @RequestParam(defaultValue = "10") int pageSize,
                                     @RequestParam(defaultValue = "") String search){
        User nowUser = userService.getNowUser(request);
        List<Company> companies = companyService.getCompanyByUser(nowUser.getId(),pageNum,pageSize,search);
        return companies;

    }

    *//**
     * 功能描述： 获取所有企业
     * 开发人员： lyx
     * 创建时间： 2020/5/6 9:25
     * 参数： [分页参数 搜索]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     *//*
    @PostMapping ("getAllCompany")
    public PageInfo<Company> getAllCompany( Integer pageNum,
                                     Integer pageSize,
                                     @RequestParam(defaultValue = "") String search){

        PageInfo<Company> companies = companyService.getAllCompany(pageNum,pageSize,search);
        List<Company> list = companies.getList();
        list.forEach(company -> company.setCompanyCredentials(companyCredentialService.selectByUseId(company.getId())));
        companies.setList(list);
        return companies;

    }

    *//**
     * 功能描述： 根据id查询企业
     * 开发人员： lyx
     * 创建时间： 2020/5/6 9:25
     * 参数： [company]
     * 返回值： com.zcyk.dto.ResultData
     * 异常：
     *//*
    @GetMapping("getCompany/{id}")
    public Company getUserCompany(@PathVariable String id){
        //1.查询基本
        Company company = companyService.getCompanyById(id);
        //2.查询资格证书
        List<CompanyCredential> companyCredentials = companyCredentialService.selectByUseId(id);
        //3.查询人员 页面没显示
        List<User>users = userCompanyService.getCompanyUsers(id);
        users.forEach(user -> user.setUserCredentials(userCredentialService.selectByUseId(user.getId())));

        return company.setCompanyCredentials(companyCredentials).setUsers(users);

    }


    *//**
     * 功能描述：获取公司及所有项目
     * 开发人员： lyx
     * 创建时间： 2020/5/8 14:17
     * 参数：
     * 返回值：
     * 异常：
     *//*
    @GetMapping("allProject")
    public List<UnitprojectGis> getCompanyProject(){
        List<UnitprojectGis> unimprovedGas = companyService.getCompanyProject();

        return unimprovedGas;
    }
*/




}