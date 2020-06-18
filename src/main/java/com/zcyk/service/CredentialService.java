package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.Credential;
import com.zcyk.entity.ProjectDepartmentCredential;

import java.util.List;

/**
 * 功能描述:证书总接口
 * 版本信息: Copyright (c)2019
 * 公司信息: 智辰云科
 * 开发人员: lyx
 * 版本日志: 1.0
 * 创建日期: 2020/4/29 11:38
 */
public interface CredentialService<T> {


    /**
     * 功能描述：根据关联ID  添加
     * 开发人员： lyx
     * 创建时间： 2020/4/29 14:59
     * 参数：
     * 返回值：
     * 异常：
     * @return
     */
    boolean addCredential(T t, String id);

    void updateCredentials(List<T> t, String id);

    boolean updateCredentials(T t);


    /**
     * 功能描述：根据关联ID查询
     * 开发人员： lyx
     * 创建时间： 2020/4/29 14:59
     * 参数：
     * 返回值：
     * 异常：
     */
    PageInfo<T> selectByUseId(String id, Integer pageSize, Integer pageNum, Integer status);
    /**
     * 功能描述：根据关联ID查询
     * 开发人员： lyx
     * 创建时间： 2020/4/29 14:59
     * 参数：
     * 返回值：
     * 异常：
     */
    T selectById(String id);

    /**
     * 功能描述：根据关联ID  code查询
     * 开发人员： lyx
     * 创建时间： 2020/4/29 14:59
     * 参数：
     * 返回值：
     * 异常：
     */
    Credential selectByCodeAndId(String id, String code);


    /**
     * 功能描述：根据关联ID code查询
     * 开发人员： lyx
     * 创建时间： 2020/4/29 14:59
     * 参数：
     * 返回值：
     * 异常：
     */
    void updateByCodeAndId(Credential credentialType);




}
