package com.zcyk.service.impl;


import com.zcyk.dao.ProjectDepartmentCredentialDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 项目证书表(ProjectGroupCredential)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
//@Service("projectDepartmentCredentialService")
@Transactional
public class ProjectCredentialServiceImpl {

    @Resource
    private ProjectDepartmentCredentialDao projectDepartmentCredentialDao;



}