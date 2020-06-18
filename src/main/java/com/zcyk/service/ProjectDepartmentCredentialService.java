package com.zcyk.service;

import com.zcyk.entity.ProjectDepartmentCredential;


/**
 * 项目证书表(ProjectGroupCredential)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface ProjectDepartmentCredentialService extends CredentialService<ProjectDepartmentCredential>{

    void updateById(ProjectDepartmentCredential projectDepartmentCredential);
}