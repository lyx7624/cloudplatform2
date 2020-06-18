package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.*;
import com.zcyk.service.ProjectDepartmentService;
import com.zcyk.service.ProjectStationService;
import com.zcyk.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 项目部门岗位2(ProjectStation)表控制层
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@RestController
@RequestMapping("projectStation")
public class ProjectStationController {
    /**
     * 服务对象
     */
    @Resource
    private ProjectStationService projectStationService;

    @Resource
    HttpServletRequest request;

    @Resource
    UserService userService;

    @Resource
    ProjectDepartmentService projectDepartmentService;



    //管理员权限#########################################


    /**
     * 功能描述：添加岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数： 上级如果是部门传部门过来
     * 返回值：
     * 异常：
     */
    @PostMapping
    public ResultData addStation(@Valid ProjectStation projectStation) throws Exception {
        String nowUserId  = userService.getNowUser(request).getId();
        //权限认证
        projectStation.setCreate_time(new Date()).setCreate_user(nowUserId) .setId(UUID.randomUUID().toString()).setStatus(1);
        if(projectStationService.addStation(projectStation)){
            return ResultData.WRITE(200,"添加成功",projectStation);
        }
        return ResultData.FAILED();



    }

    /**
     * 功能描述：删除岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @DeleteMapping
    public ResultData deleteStation(String id) throws Exception {
        //权限认证

        if(projectStationService.deleteStation(id)){
            //设置下级的直接上级为公司


            return ResultData.WRITE(200,"删除成功");
        }

        return ResultData.FAILED();


    }

    /**
     * 功能描述：修改企业岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @PutMapping ("")
    public ResultData updateStation(@Valid ProjectStation projectStation) throws Exception {
        //权限认证

        if(projectStationService.updateStation(projectStation)){
            return ResultData.WRITE(200,"修改成功");
        }
        return ResultData.FAILED();



    }

    /**
     * 功能描述：根据岗位获取岗位所在部门
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/department/{station_id}")
    public ResultData getStationDepartment( @PathVariable String station_id) throws Exception {

        String department = projectStationService.getStationDepartment(station_id);

        if(department==null){//部门被删除，项目部名称,!!!!!!!!!!!!!!!!!!!!!!可能会报错，因为删除部门将岗位部门id设置成了""
            String name = projectDepartmentService.getById(projectStationService.getById(station_id).getProject_department_id()).getName();
            return  ResultData.SUCCESS(name);
        }
        return ResultData.SUCCESS(department);

    }


    /**
     * 功能描述：查询部门下所有岗位
     * 开发人员： lyx
     * 创建时间： 2020/5/25 14:48
     * 参数：
     * 返回值：
     * 异常：
     */
    @GetMapping ("/s/{project_department_id}")
    public ResultData getDepartmentAllStation( @PathVariable String project_department_id) throws Exception {

        List<ProjectStation> companyStations = projectStationService.getDepartmentAllStation(project_department_id);

        return ResultData.SUCCESS(companyStations);

    }









}