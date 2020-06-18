package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.CompanyProjectDto;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyProject;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.entity.User;
import com.zcyk.entity.UserProjectStation;
import com.zcyk.service.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * 企业——项目组(CompanyProject)表控制层
 *
 * @author makejava
 * @since 2020-06-01 11:05:47
 */
@RestController
@RequestMapping("companyProject")
public class Company_ProjectDepartmentController {
    /**
     * 服务对象
     */
    @Resource
    private CompanyProjectService companyProjectService;


    @Resource
    UserService userService;

    @Resource
    UserCompanyStationService userCompanyStationService;
    @Resource
    private ProjectDepartmentService projectDepartmentService;

    @Resource
    HttpServletRequest request;

    @Resource
    private UserProjectStationService userProjectStationService;




    /**       管理员权限
     * 功能描述：创建项目部
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResultData addCompanyDepartment(@Valid ProjectDepartment projectDepartment,@NotNull String user_id) throws Exception {
        //重名判断
        ProjectDepartment department = projectDepartmentService.getProjectDepartmentByName(projectDepartment.getName());
        if(department != null){
            return ResultData.WRITE(400,"该名群组已存在");
        }


        if(userProjectStationService.getUserProjectDepartmentStationRecord(user_id,1).size()!=0){
            return ResultData.WRITE(400,"该用户已经有委派");
        }

        //2.创建项目部
        String nowUserId = userService.getNowUser(request).getId();
        String id = UUID.randomUUID().toString();
        projectDepartment.setCreate_time(new Date()).setAdministrator_id(nowUserId).setStatus(2).setId(id);
        if(projectDepartmentService.addProjectDepartment(projectDepartment)){
            CompanyProject companyProject = new CompanyProject().setJoin_time(new Date()).setId(UUID.randomUUID().toString())
                    .setProject_department_id(id).setCompany_id(projectDepartment.getCreate_company_id())
                    .setIn_auditor_id(nowUserId).setStatus(1).setProposer_id(nowUserId).setOperation_time(new Date()).setRole("admin");
            if(companyProjectService.joinCompanyProject(companyProject)){//2.将创建公司设置成管理公司
                //添加成功并委派
                UserProjectStation admin = new UserProjectStation().setId(UUID.randomUUID().toString()).setAppoint_id(nowUserId)
                        .setStatus(1).setJoin_time(new Date()).setRole("admin").setCompany_id(projectDepartment.getCreate_company_id())
                        .setCode(Calendar.getInstance().get(Calendar.YEAR) + "00001").setUser_id(user_id).setProject_department_id(projectDepartment.getId());

                if(!userProjectStationService.addUserToProjectDepartment(admin)){
                    throw new Exception("创建项目部委派管理员失败");
                }
                return ResultData.WRITE(200,"添加成功");
            }
            throw new Exception("创建项目部委派管理员失败");
        }
        return ResultData.WRITE(400,"添加失败");
    }


    /**
     * 功能描述：获取企业所在的群组
     * 开发人员： lyx
     * 创建时间： 2020/6/1 11:42
     * 参数： [company_id]
     * 返回值： java.util.List<com.zcyk.entity.CompanyProject>
     * 异常：
     */
    @GetMapping("/{status}/{company_id}")
    public PageInfo<CompanyProjectDto> getCompanyProject(@PathVariable String company_id, Integer pageSize, Integer pageNum, String search, @PathVariable Integer status) {
        return companyProjectService.getCompanyProject(company_id,pageSize,pageNum,search,status);
    }


    /**
     * 功能描述：修改
     * 开发人员： lyx
     * 创建时间： 2020/6/1 11:42
     * 参数： [company_id]
     * 返回值： java.util.List<com.zcyk.entity.CompanyProject>
     * 异常：
     */
    @PutMapping
    public ResultData updateCompanyProjectDepartment(String id,Integer status) {
        boolean project = companyProjectService.updateCompanyProject(new CompanyProject().setStatus(status).setId(id));
        if(project){
            return ResultData.SUCCESS();
        }
        return ResultData.FAILED();
    }


    /**
     * 功能描述：申请加入群组
     * 开发人员： lyx
     * 创建时间： 2020/6/1 11:42
     * 参数：
     * 返回值： java.util.List<com.zcyk.entity.CompanyProject>
     * 异常：
     */
    @PostMapping("/j")
    public ResultData joinCompanyProject(CompanyProject companyProject) {
        User nowUser = userService.getNowUser(request);
        if(companyProjectService.getCompanyProject(companyProject.getCompany_id(),companyProject.getProject_department_id()).size()!=0){
            return ResultData.WRITE(400,"已加入该项目部或者正在审核中");
        }

        companyProject.setId(UUID.randomUUID().toString()).setProposer_id(nowUser.getId()).setJoin_time(new Date()).setStatus(2);
        boolean isJoin = companyProjectService.joinCompanyProject(companyProject);

        if(isJoin){
            return  ResultData.WRITE(200,"申请成功，等待审核");
        }
        return  ResultData.FAILED();
    }
//
//    /**
//     * 功能描述：退出项目组
//     * 开发人员： lyx
//     * 创建时间： 2020/6/1 11:42
//     * 参数：id 数据id
//     * 返回值： java.util.List<com.zcyk.entity.CompanyProject>
//     * 异常：
//     */
//    @PutMapping("")
//    public ResultData exitCompanyProject(String id) {
//        boolean exit = companyProjectService.updateCompanyProject(new CompanyProject().setId(id).setStatus(0));
//        if(exit){
//            return ResultData.WRITE(200,"退出成功");
//        }
//        return ResultData.FAILED();
//
//    }

}