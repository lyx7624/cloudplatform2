package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dao.ProjectDepartmentCredentialDao;
import com.zcyk.dto.Credential;
import com.zcyk.entity.ProjectDepartmentCredential;
import com.zcyk.service.CredentialService;
import com.zcyk.service.ProjectDepartmentCredentialService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author WuJieFeng
 * @date 2020/6/4 14:06
 */
@Service("projectDepartmentCredentialService")
@Transactional
public class ProjectDepartmentCredentialServiceImpl implements ProjectDepartmentCredentialService {

    @Resource
    ProjectDepartmentCredentialDao projectDepartmentCredentialDao;

    @Override
    public boolean addCredential(ProjectDepartmentCredential projectDepartmentCredential, String id) {
        return projectDepartmentCredentialDao.insertSelective(projectDepartmentCredential)>0;
    }

    @Override
    public void updateCredentials(List<ProjectDepartmentCredential> t, String id) {
    }

    @Override
    public boolean updateCredentials(ProjectDepartmentCredential projectDepartmentCredential) {
        return projectDepartmentCredentialDao.updateByPrimaryKeySelective(projectDepartmentCredential)>0;
    }

    @Override
    public PageInfo<ProjectDepartmentCredential> selectByUseId(String project_department_id, Integer pageSize, Integer pageNum, Integer status) {
        if(pageSize!=null&&pageNum!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        if(status==null){
            return new PageInfo<>(projectDepartmentCredentialDao.getAllPDCredential(project_department_id,"",""));

        }
        return new PageInfo<>(projectDepartmentCredentialDao.getPDCredential(project_department_id,status,"",""));
    }

    @Override
    public ProjectDepartmentCredential selectById(String id) {
        return projectDepartmentCredentialDao.getById(id);
    }

    @Override
    public Credential selectByCodeAndId(String id, String code) {
        return null;
    }

    @Override
    public void updateByCodeAndId(Credential credentialType) {

    }

    public void updateById(ProjectDepartmentCredential projectDepartmentCredential){
        projectDepartmentCredentialDao.updateByPrimaryKeySelective(projectDepartmentCredential);
    }

//
}
