package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.CompanyProjectDto;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.CompanyProject;
import com.zcyk.entity.User;
import com.zcyk.entity.UserProjectStation;
import com.zcyk.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * 功能描述: 项目部后台--公司
 * 开发人员: xlyx
 * 创建日期: 2020/6/10 14:38
 */
@RestController
@RequestMapping("pdCompany")
public class ProjectDepartment_CompanyController {


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


    /**
     * 功能描述：获取该项目部所有公司
     * 开发人员： lyx
     * 创建时间： 2020/6/10 14:41
     * 参数： status 1在的 2需要审核的 3拒绝的 4已退出的
     * 返回值：
     * 异常：
     */
    @GetMapping("/{project_department_id}/{status}")
    public PageInfo<CompanyProjectDto> getAllCompany(@PathVariable String project_department_id, @PathVariable String status, Integer pageSize, Integer pageNum, String search){
        return companyProjectService.getProjectCompany(pageNum,pageSize,search,project_department_id,status);
    }




    /**
     * 功能描述：获取单条记录的信息
     * 1.是否需要放进公司的证书
     * 2.是否需要放进整个公司的基本信息
     *
     *
     * 开发人员： lyx
     * 创建时间： 2020/6/10 14:41
     * 参数： status 1在的 2需要审核的 3拒绝的 4已退出的
     * 返回值：
     * 异常：
     */
    @GetMapping("/{id}")
    public CompanyProjectDto getOneProjectCompany(@PathVariable String id){
        return companyProjectService.getOneProjectCompany(id);
    }
    /**
     * 功能描述：通过审核并且设置角色等
     * 开发人员： lyx
     * 创建时间： 2020/6/10 14:41
     * 参数： id  status 1在的  3拒绝的
     * 返回值：
     * 异常：
     */
    @PutMapping
    public ResultData auditAndUpdate(CompanyProject companyProject){
        User nowUser = userService.getNowUser(request);
        companyProject.setIn_auditor_id(nowUser.getId()).setOperation_time(new Date());
        if(companyProject.getStatus()==1){
            //判断该公司是不是唯一的
            CompanyProject oneRecord = companyProjectService.getOneRecord(companyProject.getId());
            List<CompanyProject> projectCompany = companyProjectService.getCompanyProject(oneRecord.getCompany_id(), oneRecord.getProject_department_id());
            long count = projectCompany.stream().filter(p -> p.getStatus() == 1).count();
            if(count==1){
                return ResultData.WRITE(200,"该公司已在项目部");
            }

            companyProject.setJoin_time(new Date());
        }
        if(companyProjectService.updateCompanyProject(companyProject)){
            return ResultData.WRITE(200,"审核成功");
        }
        return ResultData.FAILED();
    }

    /**
     * 功能描述：踢出公司出项目部
     * 开发人员： lyx
     * 创建时间： 2020/6/10 14:41
     * 参数： id  status 4
     * 返回值：
     * 异常：
     */
    @PutMapping("/kick")
    public ResultData kickedOutCompany(String id){
        User nowUser = userService.getNowUser(request);
        //踢出公司
        if(companyProjectService.updateCompanyProject(new CompanyProject().setId(id).setStatus(4)
                .setOut_auditor_id(nowUser.getId()).setOperation_time(new Date()).setQuit_time(new Date()))){
            //踢出所有成员
            CompanyProject oneProjectCompany = companyProjectService.getOneRecord(id);
            UserProjectStation userProjectStation = new UserProjectStation().setStatus(6);
            List<UserStationDto> list = userProjectStationService.getPDUsersByCompany(null, null, oneProjectCompany.getCompany_id(), oneProjectCompany.getProject_department_id(), 1).getList();
            boolean all = true;
            for (int i = 0; i < list.size(); i++) {
                if(!userProjectStationService.updateUserStationById(userProjectStation.setId(list.get(i).getId()).setStatus(6))){
                    all = false;
                }
            }
            return ResultData.WRITE(200,all?"踢出成功":"部分成员踢出失败，请手动踢出人员");
        }

        return ResultData.FAILED();
    }

    /**
     * 功能描述：删除记录
     * 开发人员： lyx
     * 创建时间： 2020/6/10 14:41
     * 参数： status 1在的 2需要审核的 3拒绝的 4已退出的
     * 返回值：
     * 异常：
     */
    @DeleteMapping
    public ResultData deleteRecord(String id){
        if(companyProjectService.updateCompanyProject(new CompanyProject().setId(id).setStatus(0))){
            return ResultData.WRITE(200,"删除成功");
        }
        return ResultData.FAILED();
    }

    /**
     * 功能描述：查询该条记录
     * 开发人员： lyx
     * 创建时间： 2020/6/10 14:41
     * 参数： status 1在的 2需要审核的 3拒绝的 4已退出的
     * 返回值：
     * 异常：
     */

}