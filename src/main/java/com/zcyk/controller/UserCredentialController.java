package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyCredential;
import com.zcyk.entity.UserCredential;
import com.zcyk.service.UserCredentialService;
import com.zcyk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.UUID;

/**
 * 功能描述: 用户证书表
 * 开发人员: xlyx
 * 创建日期: 2020/5/25 10:52
 */
@Slf4j
@RestController
@RequestMapping("userCredential")
public class UserCredentialController {


    @Resource
    UserCredentialService userCredentialService;

    @Resource
    UserService userService;


    @Resource
    HttpServletRequest request;



    /*
    * 1.用户证书所处行业怎么判断  行业应该就是类别
    *
    *
    * */


    /**
     * 功能描述：根据用户ID获取用户证书
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @GetMapping("/{id}")
    public PageInfo<UserCredential> getUserCredential(@PathVariable String id, Integer pageNum,
                                                      Integer pageSize)throws Exception{
        //这里要获取到实际的审核单位和审核人
        return userCredentialService.selectByUseId(id,pageSize,pageNum, null);
    }
    /**
     * 功能描述：获取单个证书
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @GetMapping("/c/{id}")
    public UserCredential getOneCredential(@PathVariable String id)throws Exception{
        //这里要获取到实际的审核单位和审核人
        return userCredentialService.selectById(id);
    }

    /**
     * 功能描述：新增用户证书,应该通过证书类别获取到行业，并提交审核
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @PostMapping("")
    public ResultData addCredential(@Valid UserCredential userCredential)throws Exception{
        String id = userService.getNowUser(request).getId();
        //新增证书 状态为2 还在审核中
        if(userCredentialService.addCredential((UserCredential)userCredential.setId(UUID.randomUUID().toString()).setStatus(2).setApply_time(new Date()),id)){
            return ResultData.WRITE(200,"新增成功");
        }
        return ResultData.FAILED();
    }



    /**
     * 功能描述：修改用户证书
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @PutMapping("")
    public ResultData updateCredential(UserCredential userCredential)throws Exception{
        //修改前先判断是否已经认证过了
        Integer status = userCredentialService.selectById(userCredential.getId()).getStatus();
        if(status == 1){
            return ResultData.WRITE(400,"已认证证书不能修改");
        }
        userCredentialService.updateCredentials((UserCredential)userCredential.setStatus(2));
        return ResultData.WRITE(200,"修改成功");
    }

    /**
     * 功能描述：删除用户证书、撤销认证申请
     *
     *   其实应该分开写
     * 开发人员： lyx
     * 创建时间： 2020/5/25 13:51
     * 参数： 用户id
     * 返回值：
     * 异常：
     */
    @DeleteMapping ("")
    public ResultData deleteCredential(UserCredential userCredential)throws Exception{
        if(userCredentialService.updateCredentials(userCredential)){
            return ResultData.WRITE(200,"操作成功");

        }
        return ResultData.FAILED();

    }


}