package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyCredential;
import com.zcyk.entity.ProjectDepartmentCredential;
import com.zcyk.service.ProjectDepartmentCredentialService;
import com.zcyk.service.UserService;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

/**
 * @author WuJieFeng
 * @date 2020/6/4 14:15
 */
@RestController
@RequestMapping("pdCredential")
public class ProjectDepartment_CredentialController {


    @Resource
    UserService userService;

    @Resource
    ProjectDepartmentCredentialService projectDepartmentCredentialService;


    @Resource
    HttpServletRequest request;



    /*
     * 1.用户证书所处行业怎么判断  行业应该就是类别
     *
     *
     * */


    /**
     * 功能描述：根据项目部和id获取不同状态的数据
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @GetMapping("/{id}/{status}")
    public PageInfo<ProjectDepartmentCredential> getUserCredential(@PathVariable String id, Integer pageNum,
                                                                   Integer pageSize, @PathVariable Integer status)throws Exception{
        //这里要获取到实际的审核单位和审核人
        return projectDepartmentCredentialService.selectByUseId(id,pageSize,pageNum,status);
    }
    /**
     * 功能描述：获取单位证书
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @GetMapping("/{id}")
    public PageInfo<ProjectDepartmentCredential> getCompanyAllCredential(@PathVariable String id, Integer pageNum,
                                                               Integer pageSize)throws Exception{
        //这里要获取到实际的审核单位和审核人
        return projectDepartmentCredentialService.selectByUseId(id,pageSize,pageNum,null);
    }

    /**
     * 功能描述：获取单ge证书
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @GetMapping("/c/{id}")
    public ProjectDepartmentCredential getCompanyAllCredential(@PathVariable String id)throws Exception{
        return projectDepartmentCredentialService.selectById(id);
    }

    /**
     * 功能描述：新增证书,应该通过证书类别获取到行业，并提交审核
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 谣传行业id
     * 返回值：
     * 异常：
     */
    @PostMapping
    public ResultData addCredential(@Valid ProjectDepartmentCredential projectDepartmentCredential)throws Exception{
        String id = userService.getNowUser(request).getId();
        //新增证书 状态为2 还在审核中
        if(projectDepartmentCredentialService.addCredential((ProjectDepartmentCredential)projectDepartmentCredential.setId(UUID.randomUUID().toString()).setStatus(2).setApply_time(new Date()),null)){
            return ResultData.WRITE(200,"新增成功,等待审核");
        }
        return ResultData.FAILED();
    }

    /**
     * 功能描述：修改证书
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @PutMapping
    public ResultData updateCredential(ProjectDepartmentCredential projectDepartmentCredential)throws Exception{
        //修改前先判断是否已经认证过了
        Integer status = projectDepartmentCredentialService.selectById(projectDepartmentCredential.getId()).getStatus();
        if(status == 1){
            return ResultData.WRITE(400,"已认证证书不能修改");
        }
        projectDepartmentCredentialService.updateCredentials((ProjectDepartmentCredential)projectDepartmentCredential.setStatus(2));
        return ResultData.WRITE(200,"修改成功");
    }

    /**
     * 功能描述：删除用户证书、撤销认证申请
     *
     *   其实应该分开写
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id status 传0
     * 返回值：
     * 异常：
     */
    @DeleteMapping
    public ResultData deleteCredential(ProjectDepartmentCredential projectDepartmentCredential)throws Exception{
        if(projectDepartmentCredentialService.updateCredentials(projectDepartmentCredential)){
            return ResultData.WRITE(200,"操作成功");

        }
        return ResultData.FAILED();

    }



}
