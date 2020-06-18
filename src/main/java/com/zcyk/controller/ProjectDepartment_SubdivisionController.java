package com.zcyk.controller;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.*;
import com.zcyk.service.ProjectDepartmentService;
import com.zcyk.service.ProjectDepartmentSubdivisionService;
import com.zcyk.service.ProjectStationService;
import com.zcyk.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 公司——部门(ProjectDepartmentSubdivision)表控制层
 *
 * @author makejava
 * @since 2020-06-10 09:25:06
 */
@RestController
@RequestMapping("pdSubdivision")
public class ProjectDepartment_SubdivisionController {
    /**
     * 服务对象
     */
    @Resource
    private ProjectDepartmentSubdivisionService pdsService;

    @Resource
    private ProjectStationService projectStationService;
    @Resource
    private ProjectDepartmentService projectDepartmentService;

    @Resource
    HttpServletRequest request;

    @Resource
    UserService userService;
    /**
     * 功能描述：新增
     * 开发人员： lyx
     * 创建时间： 2020/6/10 9:42
     * 参数：
     * 返回值：
     * 异常：
     */
    @PostMapping
    public ResultData addDepartment(@Valid ProjectDepartmentSubdivision projectDepartmentSubdivision){
        String user_id = userService.getNowUser(request).getId();
        //权限认证

       return pdsService.addDepartmentSubdivision
                (projectDepartmentSubdivision.setCreate_time(new Date()).setId(UUID.randomUUID().toString())
                        .setAdministrator_id(user_id).setStatus(1));

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
        HashMap<Object, Object> map = new HashMap<>();
        List<ProjectDepartmentSubdivision> underDepartments = pdsService.getUnderDepartment(superior_id);

        List<ProjectStation> underStations = projectStationService.getUnderStation(superior_id);
        map.put("childDepartment",underDepartments);
        map.put("childStation",underStations);
        return ResultData.SUCCESS(map);

    }

    /**
     * 功能描述：删除部门
     * 开发人员： lyx
     * 创建时间： 2020/6/10 9:42
     * 参数：
     * 返回值：
     * 异常：
     */
     @DeleteMapping
     @Transactional(rollbackFor = Exception.class)
     public ResultData deleteCompanyDepartment(String id) throws Exception {
         //权限认证
         pdsService.deleteDepartmentAndUnder(id);
         return ResultData.WRITE(200,"删除成功");

     }

     /**
     * 功能描述：修改
     * 开发人员： lyx
     * 创建时间： 2020/6/10 9:42
     * 参数：
     * 返回值：
     * 异常：
     */


}