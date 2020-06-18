package com.zcyk.controller;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.LoginUserMap;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.*;
import com.zcyk.myenum.ResultCode;
import com.zcyk.service.*;
import com.zcyk.util.SendSms;
import com.zcyk.util.ValidateCode;
import lombok.extern.slf4j.Slf4j;
import net.jodah.expiringmap.ExpirationPolicy;
import net.jodah.expiringmap.ExpiringMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用户信息表
 (User)表控制层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Slf4j
@RestController
@RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    @Resource
    private UserProjectService userProjectService;

    @Resource
    UserCredentialService userCredentialService;

    @Resource
    HttpServletRequest request;




    /*验证码全局属性*/
    ExpiringMap<String, String> map = ExpiringMap.builder().variableExpiration()
            .expiration(6000 * 10 * 6, TimeUnit.MILLISECONDS)//验证码有效期6分钟
            .expirationPolicy(ExpirationPolicy.CREATED)//创建
            .maxSize(100000)
            .build();


    /**
     * 功能描述：获取用户信息
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     */
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id)throws Exception{
        return userService.getById(id);
    }


    /**
     * 功能描述：修改用户信息
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [id 用户id]
     * 返回值： com.zcyk.dto.ResultData
     */
    @PutMapping
    public ResultData updateUser(@Valid User user)throws Exception{
        User nowUser = userService.getNowUser(request);
        if(nowUser.getStatus()==2){
            if(userService.update(user)){
                return ResultData.WRITE(200,"修改成功，等待认证");
            }
        }else {//已认证或者被冻结
            return ResultData.WRITE(400,"已认证用户不能修改");
        }
        return ResultData.WRITE(400,"修改失败");


    }










    /**
     * 功能描述：获取短信验证码
     * 开发人员： lyx
     * 创建时间： 2019/7/25 14:04
     * 参数： [user_account]
     * 返回值： com.zcyk.dto.ResultData
     */
    @GetMapping(value="/getCode")
    public ResultData getCode(String user_account, HttpServletResponse response)throws Exception{
        //是否设置恶意发送
//        ResultData resultData = userServiceImpl.judgeUser(new User().setUser_account(user_account));
//        if("404".equals(resultData.getStatus())){
//            return resultData;
//        }
        String code;
        try {
            code = SendSms.sendMessage(user_account);
        } catch (Exception e) {
            log.error("验证码接口错误",e);
            return ResultData.WRITE(ResultCode.ERROR);
        }

        if(StringUtils.isNotBlank(code)){
            /*设置验证码有效期6分钟*/
            map.put(user_account, code);
            return ResultData.SUCCESS(map.get(user_account));
        }
        return ResultData.FAILED();
    }


    /**
     * 功能描述：图片验证码
     * 开发人员： lyx
     * 创建时间： 2019/7/25 17:54
     */
    @GetMapping("/getCodePic")
    public ResultData pic(HttpServletResponse response)throws Exception{
        ValidateCode vCode = new ValidateCode(160,50,5,150);//获取图片
        response.setContentType("image/png;charset=utf-8");
        try {
            ServletOutputStream outputStream = response.getOutputStream();
//            response.setHeader("Access-Control-Expose-Headers", "SessionId,code,Content-Type");
            vCode.write(outputStream);
        } catch (IOException e) {
            log.error("登录获取图片验证码失败",e);
            return ResultData.WRITE(ResultCode.ERROR);
        }
        return ResultData.SUCCESS();
    }

    /**
     * 功能描述：超管登录
     * 开发人员：Wujiefeng
     * 创建时间：2020/5/25 14:20
     * 参数：[ * @param null]
     * 返回值：
    */
    @PostMapping("/supperLogin")
    public ResultData supperManagerLogin(User user,HttpServletResponse response){
       return userService.supperManagerLogin(user,response);
    }

    /**
     * 功能描述：登录
     * 开发人员： lyx
     * 创建时间： 2019/7/24 17:54
     * 参数： [user]
     * 返回值： com.zcyk.dto.ResultData
     */
    @PostMapping("/login")
    public ResultData login(User user, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(/*user.getCode()==null || */StringUtils.isBlank(user.getPassword())){
            return ResultData.FAILED("验证码/密码等不能为空");
        }

        return userService.loginByPwd(user,response);

    }

    /**
     * 功能描述：退出登录
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [user]
     * 返回值： com.zcyk.dto.ResultData
     */
    @PostMapping("/loginOut")
    public void loginOut(HttpServletRequest request)throws Exception{
        String id = userService.getNowUser(request).getId();
        LoginUserMap.removeUser(id);
    }



    /**
     * 功能描述：注册
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [user]
     * 返回值： com.zcyk.dto.ResultData
     */
    @PostMapping("/register")
    public ResultData register(User user)throws Exception{
        return userService.register(user,map.get(user.getAccount()));
//        if("200".equals(resultData.getCode()))map.remove(user.getAccount());//删除验证码
//        return ResultData.SUCCESS();
    }

    /**修改需要验证码
     * 功能描述：忘记密码，同时修改
     * 1.短信重置密码
     * 2.短信修改密码  都只写入新的密码
     * 开发人员： lyx
     * 创建时间： 2019/7/25 10:42
     * 参数： [user  ！！传过来的user账号要是新账号]
     * 返回值： com.zcyk.dto.ResultData
     */
    @PostMapping("/updatePwd")
    public ResultData updatePwd(User user)throws Exception{
        ResultData resultData = userService.updatePwd(user, map.get(user.getAccount()));
        if("200".equals(resultData.getCode()))map.remove(user.getAccount());
        return resultData;

    }

}