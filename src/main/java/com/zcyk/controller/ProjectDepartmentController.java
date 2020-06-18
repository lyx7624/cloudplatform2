package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.*;
import com.zcyk.service.CompanyProjectService;
import com.zcyk.service.ProjectDepartmentService;
import com.zcyk.service.UserProjectStationService;
import com.zcyk.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 项目——部门(ProjectDepartment)表控制层
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@RestController
@RequestMapping("projectDepartment")
public class ProjectDepartmentController {
    /**
     * 服务对象
     */
    @Resource
    private ProjectDepartmentService projectDepartmentService;




    @Resource
    HttpServletRequest request;

    @Resource
    UserService userService;

    @Resource
    private CompanyProjectService companyProjectService;

    @Resource
    private UserProjectStationService userProjectStationService;






    /**
     * 功能描述：修改部门
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @PutMapping ("/")
    public ResultData updateCompanyDepartment(ProjectDepartment projectDepartment) throws Exception {

        if(projectDepartmentService.updateProjectDepartment(projectDepartment)){
            return ResultData.WRITE(200,"修改成功");
        }
        return ResultData.FAILED();



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
        List<ProjectDepartment> companyDepartments = projectDepartmentService.getUnderDepartmentStation(superior_id);
        return ResultData.SUCCESS(companyDepartments);

    }

    /**
     * 功能描述：根据名称模糊搜索
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/name")
    public ResultData searchProjectDepartmentByName(String name) throws Exception {
        return ResultData.SUCCESS(projectDepartmentService.searchProjectDepartmentByName(name));

    }


    /**
     * 功能描述：获取用户可进入的项目部
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/user/{id}")
    public ResultData getAdminUserProjectDepartment(@PathVariable String id) throws Exception {
        return ResultData.SUCCESS(projectDepartmentService.getAdminUserProjectDepartment(id));

    }

    /**
     * 功能描述：进入
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/user/{user_id}/{project_department_id}")
    public ResultData getAdminUserProjectDepartment(@PathVariable String user_id, @PathVariable String project_department_id) throws Exception {
        Map<String, Object> map = new HashMap<>();

        PageInfo<UserStationDto> station = userProjectStationService.getUserProjectDepartmentStation(null, null, user_id, project_department_id, 1);
        List<UserStationDto> list = station.getList();
        if(list.size()>1){
            return ResultData.WRITE(400,"数据错乱。联系运维人员");
        }
        UserStationDto userStationDto = list.get(0);

        ProjectDepartment projectDepartment = projectDepartmentService.getById(project_department_id);

        map.put("userStation",userStationDto);
        map.put("projectDepartment",projectDepartment);

        return ResultData.SUCCESS(map);

    }


    //################################################### 项目部后台公司





}