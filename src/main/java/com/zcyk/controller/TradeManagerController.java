package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.Credential;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.*;
import com.zcyk.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author WuJieFeng
 * @date 2020/6/3 10:09
 */
@Slf4j
@RestController
@RequestMapping("tradeManager")
public class TradeManagerController {
    @Resource
    CompanyService companyService;
    @Resource
    private TradeService tradeService;
    @Resource
    private DistrictService districtService;
    @Resource
    private UserCredentialService userCredentialService;
    @Resource
    private CompanyCredentialService companyCredentialService;
    @Resource
    private ProjectDepartmentService projectDepartmentService;
    @Resource
    private ProjectDepartmentCredentialService projectDepartmentCredentialService;
    @Resource
    private UserService userService;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserCompanyStationService companyStationService;

    /**
     * 功能描述：（行管）查看行业中个人所有证书审核
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/2 17:57
     * 参数：[ * @param null]
     * 返回值：
     */
    @GetMapping("{trade_id}/{area_code}/{status}")//status=2 未审核
    public ResultData getTradeUserCredential(@PathVariable String trade_id,
                                             @PathVariable int status,
                                             @PathVariable String area_code,
                                              String type,
                                              String level,
                                             Integer pageNum,
                                             Integer pageSize) throws Exception {
        try {

            PageInfo<UserCredential> userCredentials = userCredentialService.getUserCredentialByTrade(trade_id, status,area_code,type,level, pageNum, pageSize);
            return ResultData.SUCCESS(userCredentials);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("查询错误");
        }
    }

    /**
     * 功能描述：（行管）个人证书认证
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 10:32
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("{id}/{status}")
    public ResultData updateUserCredentialStatus(@PathVariable String id,@PathVariable int status) throws Exception {
        try {
            User nowUser = userService.getNowUser(request);
            UserCompanyStation userCompanyStation = companyStationService.getCompanyStationByUser(nowUser.getId());

            UserCredential userCredential = new UserCredential();
            userCredential.setId(id).setStatus(status).setExamine_user_id(nowUser.getId()).setExamine_unit_id(userCompanyStation.getCompany_id());
            userCredentialService.updateCredentials(userCredential);
            return ResultData.SUCCESS();
        }catch (Exception e){
            throw new Exception("修改失败");
        }

    }

    /**
     * 功能描述：(行管)查看行业中所有企业证书（待认证，已认证）
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 11:58
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("company/{trade_id}/{area_code}/{status}")//status =2 未审核的
    public ResultData getCompanyByTradeManager(@PathVariable String trade_id,
                                               @PathVariable int status,
                                               @PathVariable String area_code,
                                               String type,
                                               String level,
                                               Integer pageNum,
                                               Integer pageSize) throws Exception {
        try {
            PageInfo<CompanyCredential> companyByTradeManager = companyService.getCompanyByTradeManager(trade_id, status, area_code, type, level,pageNum, pageSize);

            return ResultData.SUCCESS(companyByTradeManager);
        }catch (Exception e){
            e.printStackTrace();
            throw new Exception("查询失败");
        }
    }


    /**
     * 功能描述：（行管）企业证书认证
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/3 10:32
     * 参数：[ * @param null]
     * 返回值：
     */
    @PutMapping("company/{id}/{status}")
    public ResultData updateCompanyCredentialStatus(@PathVariable String id,@PathVariable int status) throws Exception {
        try {
            User nowUser = userService.getNowUser(request);
            UserCompanyStation userCompanyStation = companyStationService.getCompanyStationByUser(nowUser.getId());

            CompanyCredential companyCredential = new CompanyCredential();
            companyCredential.setId(id).setStatus(status).setExamine_user_id(nowUser.getId()).setExamine_unit_id(userCompanyStation.getCompany_id());
            companyCredentialService.updateCredentials(companyCredential);
            return ResultData.SUCCESS();
        }catch (Exception e){
            throw new Exception("修改失败");
        }

    }

//    /**
//     * 功能描述：(行管)查询行业内群组证书
//     * 开发人员：Wujiefeng
//     * 创建时间：2020/6/3 15:42
//     * 参数：[ * @param null]
//     * 返回值：
//    */
//    @GetMapping("projectDepartment/{trade_id}/{area_code}/{status}")
//    public ResultData getPDCredentialByTradeManager(@PathVariable String trade_id,
//                                               @PathVariable int status,
//                                               @PathVariable String area_code,
//                                               String type,
//                                               Integer pageNum,
//                                               Integer pageSize) throws Exception {
//
//    }
    /**
     * 功能描述：（行管）查看项目部证书
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/4 15:20
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("projectDepartment/{trade_id}/{area_code}/{status}")
    public ResultData getPDCredentialByTradeManager(@PathVariable String trade_id,
                                               @PathVariable int status,
                                               @PathVariable String area_code,
                                               String type,
                                               String level,
                                               Integer pageNum,
                                               Integer pageSize){
        PageInfo<ProjectDepartmentCredential> pdCredentialByTradeManager = projectDepartmentService.getPDCredentialByTradeManager(trade_id, status, area_code, type, level, pageNum, pageSize);
        return ResultData.SUCCESS(pdCredentialByTradeManager);
    }

    /**
     * 功能描述：项目部证书认证
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/10 10:15
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("projectDepartment/{id}/{status}")
    public ResultData updateProjectDepartmentCredentialStatus(@PathVariable String id,@PathVariable int status) throws Exception {
        try {
            User nowUser = userService.getNowUser(request);
            UserCompanyStation userCompanyStation = companyStationService.getCompanyStationByUser(nowUser.getId());

            ProjectDepartmentCredential projectDepartmentCredential = new ProjectDepartmentCredential();
            projectDepartmentCredential.setId(id).setStatus(status).setExamine_user_id(nowUser.getId()).setExamine_unit_id(userCompanyStation.getCompany_id());
            projectDepartmentCredentialService.updateById(projectDepartmentCredential);
            return ResultData.SUCCESS();
        }catch (Exception e){
            throw new Exception("修改失败");
        }

    }

    /**
     * 功能描述：判断是否区管行管
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/10 14:54
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("isTD")
    public ResultData isManager(){
        Map<String,Object> map = new HashMap<>();
        User nowUser = userService.getNowUser(request);
        UserCompanyStation userCompanyStation = companyStationService.getCompanyStationByUser(nowUser.getId());
        if (userCompanyStation.getCompany_id()==null){
            return ResultData.WRITE(400,"未加入任何企业");
        }
        String company_id = userCompanyStation.getCompany_id();
        Trade_company tradeManager = tradeService.isTradeManager(company_id);
        District districtManager = districtService.isDistrictManager(company_id);
        Company company = companyService.getCompanyById(company_id);
        if (tradeManager==null){
            map.put("trade_role",null);
        }else {
            Trade oneTrade = tradeService.getOneTrade(tradeManager.getTrade_id());
            String trade_code = tradeManager.getTrade_code();
            String area_code = trade_code.substring(0,6);
            map.put("trade_role",tradeManager.setTrade_name(oneTrade.getName()).setTrade_code(area_code).setCompany_name(company.getName()).setCompany_logo(company.getLogo_url()));
        }

        if(districtManager==null){
            map.put("district_role",null);
        }else {
            map.put("district_role",districtManager.setCompany_name(company.getName()).setCompany_logo(company.getLogo_url()));
        }
        return ResultData.SUCCESS(map);
    }
}
