package com.zcyk.controller;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.ProjectProcess;
import com.zcyk.entity.User;
import com.zcyk.service.ProjectProcessService;
import com.zcyk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author WuJieFeng
 * @date 2020/6/8 16:35
 */
@Slf4j
@RestController
@RequestMapping("projectProcess")
public class ProjectProcessController {
    @Resource
    private UserService userService;
    @Resource
    private ProjectProcessService projectProcessService;
    @Resource
    private HttpServletRequest request;

    /**
     * 功能描述：发起流程（添加上级、下级）
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/8 16:37
     * 参数：[ * 发起人项目id:initiator_project_id ,审核人项目id：handler_project_id,状态 status]
     * 返回值：
    */
    @PostMapping("")
    public ResultData addProcess(ProjectProcess projectProcess){
        User nowUser = userService.getNowUser(request);
        return projectProcessService.addProcess(projectProcess.setInitiator_id(nowUser.getId()));
    }

    /**
     * 功能描述：处理流程
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/8 16:49
     * 参数：[ * @param null]
     * 返回值：
    */
    @PutMapping("{id}/{status}")
    public ResultData handleProcess(@PathVariable String id,@PathVariable Integer status){
        User nowUser = userService.getNowUser(request);
        ProjectProcess projectProcess = new ProjectProcess().setId(id).setStatus(status).setHandler_id(nowUser.getId());
        return projectProcessService.handleProcess(projectProcess);
    }

    /**
     * 功能描述：查询流程
     * 开发人员：Wujiefeng
     * 创建时间：2020/6/8 16:49
     * 参数：[ * @param null]
     * 返回值：
    */
    @GetMapping("{project_id}/{type}")
    public ResultData getProcess(@PathVariable String project_id,@PathVariable Integer type,
                                 Integer pageNum,Integer pageSize){
        return projectProcessService.getProcess(project_id,type,pageNum,pageSize);
    }


}
