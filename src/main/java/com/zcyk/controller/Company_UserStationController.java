package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.*;
import com.zcyk.service.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 用户公司岗位表(UserCompanyStation)表控制层
 *
 * @author makejava
 * @since 2020-05-25 14:40:38
 */
@RestController
@RequestMapping("/userStation")
public class Company_UserStationController {
    /**
     * 服务对象
     */
    @Resource
    private UserCompanyStationService userCompanyStationService;

    @Resource
    private CompanyStationService companyStationService;

    @Resource
    private UserProjectStationService userProjectStationService;



    @Resource
    CompanyService companyService;

    @Resource
    HttpServletRequest request;

    @Resource
    UserService userService;



    boolean getPermissions(String user_id,String company_id){
        UserCompanyStation userCompany = userCompanyStationService.getUserCompany(user_id, company_id);
        if ("user".equals(userCompany.getRole())){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 功能描述：获取历史岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：String user_id,String company_id
     * 返回值：
     * 异常：
     */
    @GetMapping("/history/{user_id}/{company_id}")
    public ResultData getHistoryStation(Integer pageNum,
                                        Integer pageSize, @PathVariable String company_id, @PathVariable String user_id) throws Exception {

        //权限认证
        PageInfo<UserStationDto> userCompanyStations = userCompanyStationService.getHistoryStation(user_id,company_id,pageNum,pageSize);

        return ResultData.SUCCESS(userCompanyStations);


    }
    /**
     * 功能描述：根据id 获取岗位信息
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：String user_id,String company_id
     * 返回值：
     * 异常：
     */
    @GetMapping("{id}")
    public ResultData getUserByProjectDepartment(@PathVariable String id){
        UserStationDto projectUser = userCompanyStationService.getCompanyUserById(id);
        return ResultData.SUCCESS(projectUser);
    }


    /**
     * 功能描述：根据用户ID获取 公司
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @GetMapping("/company/{id}")
    public PageInfo<Company> getUserCompany(@PathVariable String id,
                                            Integer pageNum,
                                            Integer pageSize)throws Exception{
        return userCompanyStationService.getUserManageCompany(id,pageNum,pageSize);
    }


    /**
     * 功能描述：获取所有审核人员 status = 2
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @GetMapping("/company/user/2/{company_id}")
    public ResultData auditUser(@PathVariable String company_id, Integer pageNum,
                                Integer pageSize)throws Exception{
        PageInfo<UserCompanyStation> userCompanies = userCompanyStationService.gatAuditUser(company_id,pageSize,pageNum);
        return ResultData.SUCCESS(userCompanies);
    }

    /**
     * 功能描述：获取下级
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @GetMapping("/superior/{user_id}")
    public ResultData auditUser(String search, Integer pageNum,
                                Integer pageSize, @PathVariable String user_id)throws Exception{
        PageInfo<User> userCompanies = userCompanyStationService.gatUserUnder(search,user_id,pageSize,pageNum);
        return ResultData.SUCCESS(userCompanies);
    }







    /**
     * 功能描述：申请加入公司
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @PostMapping("/company")
    public ResultData userJoinCompany(UserCompanyStation userStation)throws Exception{
        User nowUser = userService.getNowUser(request);
        //判断是否在其他公司
        PageInfo<Company> pageInfo = userCompanyStationService.getByUserId(nowUser.getId(), null, null);
        List<Company> userCompanyStations = pageInfo.getList();
        //已经在公司
        int in = userCompanyStations.stream().filter(userCompanyStation -> userCompanyStation.getStatus() == 1).collect(Collectors.toList()).size();
        if(in!=0){
            return ResultData.WRITE(400,"已在其他公司");
        }
        //正在审核的公司
        int on = userCompanyStations.stream().filter(userCompanyStation -> userCompanyStation.getStatus() == 2).collect(Collectors.toList()).size();
        if(on!=0){
            return ResultData.WRITE(400,"已有申请，请等待正在审核的公司");
        }


        //1.是否需要密码
        Company company = companyService.getCompanyById(userStation.getCompany_id());
        if(StringUtils.isNotBlank(company.getUser_join_password())){
            if(userStation.getUser_join_password()==null){
                return ResultData.WRITE(401,"请输入加入密码");
            }
            if(!userStation.getUser_join_password().equals(company.getUser_join_password())){
                return ResultData.WRITE(401,"密码错误");
            }

        }
        if(userStation.getRole()==null){
            userStation.setRole("user");
        }


        if(!userCompanyStationService.addUserToCompanyStation(userStation.setJoin_time(new Date()).setStatus(2).setId(UUID.randomUUID().toString()))){
            return ResultData.FAILED();

        }

        return ResultData.WRITE(200,"加入成功，等待审核");
    }

    /**
     * 功能描述：撤销加入公司
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id该条数据id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @PutMapping("/company/u")
    public ResultData undoJoinCompany(String id)throws Exception{
        if(userCompanyStationService.updateById(new UserCompanyStation().setId(id).setStatus(5))){
            return ResultData.WRITE(200,"撤销成功");
        }
        return ResultData.FAILED();
    }
     /**
     * 功能描述：拒绝加入公司
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id该条数据id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @PutMapping("/company/refuse")
    public ResultData refuseJoinCompany(String id)throws Exception{
        if(userCompanyStationService.updateById(new UserCompanyStation().setId(id).setStatus(3))){
            return ResultData.WRITE(200,"已拒绝");
        }
        return ResultData.FAILED();
    }


    /**
     * 功能描述：入职
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @PutMapping("/company/station")
    public ResultData induction(UserCompanyStation userCompanyStation) throws Exception {
        //权限认证
        String user_id = userService.getNowUser(request).getId();
        if(!getPermissions(user_id,userCompanyStation.getCompany_id())){
            return ResultData.WRITE(400,"没有权限");
        }
        //岗位负责人只能有一个！！！！所以在显示界面要显示出岗位是否有技术负责人

        //设置用户是否是企业副管理员  是不是只有企业管理员能设置！！
        if("admin_ii".equals(userCompanyStation.getRole())){//设置副管
            UserCompanyStation role = userCompanyStationService.getUserCompany(user_id, userCompanyStation.getCompany_id());
            if(!"admin".equals(role.getRole())){
                return ResultData.WRITE(400,"只有一级管理员能添加二级管理员");
            }
        }

        if("1".equals(userCompanyStation.getSuperior())){//设置他为岗位管负责人
            companyStationService.updateStation(new CompanyStation().setId(userCompanyStation.getStation_id()).setAdministrator_id(userCompanyStation.getUser_id()));
        }

        //查询公司人员数量设置员工编号 年份+0000 +total 可能出现多线程编码重合
        long total = userCompanyStationService.gatAllUser(
                userCompanyStationService.getById(userCompanyStation.getId()).getCompany_id(), null, null,"", 1).getTotal();

        if(!userCompanyStationService.updateById(userCompanyStation.setJoin_time(new Date()).setStatus(1).setIn_auditor_id(user_id)
                .setOperation_time(new Date())
                .setCode(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)*10000+total+1)))){
            return ResultData.FAILED();
        }

        return ResultData.WRITE(200,"入职成功");
    }






    /**  未用
     * 功能描述：退出公司
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @PutMapping("/company")
    public ResultData quitCompany(String id)throws Exception{
        User nowUser = userService.getNowUser(request);
//
//        UserCompanyStation quitUser = userCompanyStationService.getUserCompany(nowUser.getId(), company_id);
//        if("admin".equals(quitUser.getRole())){//主管
//            return ResultData.WRITE(400,"一级管理员不能推出企业");
//        }
        //判断是否是岗位负责人，获取在管理岗位
//        List<CompanyStation> administratorStations = companyStationService.getAdministratorStation(nowUser.getId());
//        administratorStations.forEach(administratorStation->{//将岗位负责人设置成无
//            companyStationService.updateStation(administratorStation.setAdministrator_id(""));
//        });


        boolean update = userCompanyStationService.updateById(new UserCompanyStation().setId(id).setStatus(6));
        if(update){
            return ResultData.WRITE(200,"成功推出企业");
        }
        return ResultData.FAILED();
    }


    /**
     * 功能描述：修改岗位信息
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：String String station_id,String id,role
     * 返回值：
     * 异常：
     */
    @PutMapping("/station")
    public ResultData updateStation(UserCompanyStation userCompanyStation) throws Exception {
        UserCompanyStation station = userCompanyStationService.getById(userCompanyStation.getId());

        if(userCompanyStation.getRole()==null){//可以不选择是否是管理员
            userCompanyStation.setRole("user");
        }
        if(userCompanyStation.getStation_id()==null){//可以不选择岗位
            userCompanyStation.setStation_id("");
        }

        //权限认证
        String user_id  = userService.getNowUser(request).getId();

        if("admin".equals(station.getRole()) && !"admin".equals(userCompanyStation.getRole())){
            return ResultData.WRITE(400,"超管角色不能更改");
        }

        if(!getPermissions(user_id,station.getCompany_id())){
            return ResultData.WRITE(400,"没有权限");
        }

        if(station.getStation_id()!=null && !station.getStation_id().equals(userCompanyStation.getStation_id())){//更换了岗位
            if(!userCompanyStationService.replaceStation(station.setOut_auditor_id(user_id)
                    .setStation_id(userCompanyStation.getStation_id()).setSuperior_id(userCompanyStation.getSuperior_id())
                    .setRole(userCompanyStation.getRole()))){
                return ResultData.WRITE(400,"修改岗位失败");
            }
        }else {
            if(!userCompanyStationService.updateById(userCompanyStation)){
                return ResultData.WRITE(400,"修改岗位失败");
            }
        }

        return ResultData.WRITE(200,"用户信息已修改");
    }






    /**
     * 功能描述：踢出岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @DeleteMapping ("/station/{id}")
    public ResultData deleteStation(@PathVariable String id) throws Exception {

        UserCompanyStation userCompanyStation = userCompanyStationService.getById(id);
        //获取用户委派 1正在项目中 2申请中
        int size = userProjectStationService.getUserProjectDepartmentStationRecord(userCompanyStation.getUser_id(), 1).size();
        int size2 = userProjectStationService.getUserProjectDepartmentStationRecord(userCompanyStation.getUser_id(), 2).size();
        if(size!=0 || size2!=0){
            return ResultData.WRITE(400,"用户有委派任务正在进行");
        }

        //权限认证
        String nowUser = userService.getNowUser(request).getId();
        if(!getPermissions(nowUser,userCompanyStation.getCompany_id())){
            return ResultData.WRITE(400,"没有权限");
        }




        if(!userCompanyStationService.updateStationById(
                new UserCompanyStation().setId(id).setStatus(6).setOut_auditor_id(nowUser).setQuit_time(new Date()))){
            return ResultData.FAILED();
        }
        return ResultData.WRITE(200,"成功踢出该成员");
    }





    /**
     * 功能描述：删除记录 加入公司
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id该条数据id]
     * 返回值： com.zcyk.dto.ResultData
     * @return
     */
    @DeleteMapping("/company/d")
    public ResultData deleteJoinCompany(String id)throws Exception{
        if(userCompanyStationService.updateById(new UserCompanyStation().setId(id).setStatus(0))){

            return ResultData.WRITE(200,"删除成功");
        }
        return ResultData.FAILED();
    }

}