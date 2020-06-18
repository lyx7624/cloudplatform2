package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户信息表
(User)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface UserService {




    /*获取当前登录用户*/
    User getNowUser(HttpServletRequest request);
    /*超管登录*/
    ResultData supperManagerLogin(User user,HttpServletResponse response);

    /*密码登录*/
    ResultData loginByPwd(User user, HttpServletResponse response);
    //ResultData loginByPwd(User user, HttpServletRequest request, HttpServletResponse response);
    /*用户注册*/
    ResultData register(User user, String code);
   // ResultData register(User user);

    ResultData updatePwd(User user, String code);

    boolean update(User user) throws Exception;


    /*根据id获取用户*/
    User getById(String id);

    /*根据电话号码获取用户*/
    User getByAccount(String id);

    PageInfo<User> getCompanyUsers(String id, Integer pageNum, Integer pageSize, String level, String type);

    List<User> getUnCompanyUsers();

    /*根据区域查人员*/
    PageInfo<User> getDistrictUser(String area_code,int status,String search,Integer pageNum,Integer pageSize);
}