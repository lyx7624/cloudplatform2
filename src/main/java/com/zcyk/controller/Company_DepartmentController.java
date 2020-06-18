package com.zcyk.controller;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyDepartment;
import com.zcyk.entity.CompanyStation;
import com.zcyk.entity.UserCompanyStation;
import com.zcyk.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.persistence.Transient;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 功能描述: 公司部门
 * 开发人员: xlyx
 * 创建日期: 2020/5/25 14:32
 */
@Slf4j
@RestController
@RequestMapping("/companyDepartment")
public class Company_DepartmentController {

    @Resource
    CompanyDepartmentService companyDepartmentService;
    @Resource
    CompanyStationService companyStationService;

    @Resource
    CompanyService companyService;


    @Resource
    HttpServletRequest request;

    @Resource
    UserService userService;

    @Resource
    private UserCompanyStationService userCompanyStationService;


     boolean getPermissions(String user_id,String company_id){
         UserCompanyStation userCompany = userCompanyStationService.getUserCompany(user_id, company_id);
         if ("user".equals(userCompany.getRole())){
             return false;
         }else {
             return true;
         }
     }

    /**
     * 功能描述：添加企业部门
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @PostMapping
    public ResultData addCompanyDepartment(@Valid CompanyDepartment companyDepartment) throws Exception {
        String user_id = userService.getNowUser(request).getId();
        //权限认证

        return companyDepartmentService.addCompanyDepartment
                (companyDepartment.setCreate_time(new Date()).setId(UUID.randomUUID().toString())
                                .setAdministrator_id(user_id).setStatus(1));


    }

    /**
     * 功能描述：删除企业部门
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @DeleteMapping
    @Transactional(rollbackFor = Exception.class)
    public ResultData deleteCompanyDepartment(String company_id, String id) throws Exception {
        //权限认证
        companyDepartmentService.deleteUnderStation(id);
        return ResultData.WRITE(200,"删除成功");

    }

    /**
     * 功能描述：修改企业部门
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @PutMapping
    public ResultData updateCompanyDepartment(@Valid CompanyDepartment companyDepartment,String company_id) throws Exception {
        //权限认证
        String user_id = userService.getNowUser(request).getId();
        if(!getPermissions(user_id,company_id)){
            return ResultData.WRITE(400,"没有权限");
        }
        return companyDepartmentService.updateCompanyDepartment(companyDepartment);



    }


    /**
     * 功能描述：获取部门下级部门和岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/under/{superior_id}")
    public ResultData getUnderDepartmentStation(@PathVariable String superior_id) throws Exception {
        List<CompanyDepartment> underDepartments = companyDepartmentService.getUnderDepartment(superior_id);

        List<CompanyStation>  underStations = companyStationService.getUnderStation(superior_id);



        HashMap<Object, Object> map = new HashMap<>();
        map.put("childDepartment",underDepartments);
        map.put("childStation",underStations);
        return ResultData.SUCCESS(map);

    }












}