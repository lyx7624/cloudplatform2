package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dao.ProjectDepartmentCredentialDao;
import com.zcyk.dto.CompanyProjectDto;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.dao.ProjectDepartmentDao;
import com.zcyk.entity.ProjectDepartmentCredential;
import com.zcyk.service.ProjectDepartmentService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 公司——部门(ProjectDepartment)表服务实现类
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@Service("projectDepartmentService")
public class ProjectDepartmentServiceImpl implements ProjectDepartmentService {
    @Resource
    private ProjectDepartmentDao projectDepartmentDao;
    @Resource
    private ProjectDepartmentCredentialDao projectDepartmentCredentialDao;

    @Override
    public boolean addProjectDepartment(ProjectDepartment projectDepartment) {

        return projectDepartmentDao.insertSelective(projectDepartment)>0;
    }

    @Override
    public boolean updateProjectDepartment(ProjectDepartment projectDepartment) {
        return projectDepartmentDao.updateByPrimaryKeySelective(projectDepartment)>0;
    }

    @Override
    public List<ProjectDepartment> getUnderDepartmentStation(String superior_id) {
        return projectDepartmentDao.getUnderDepartmentStation(superior_id);
    }

    @Override
    public List<ProjectDepartment> searchProjectDepartmentByName(String name) {

        return projectDepartmentDao.searchProjectDepartmentByName(name);
    }

    @Override
    public ProjectDepartment getProjectDepartmentByName(String name) {
        Example example = new Example(ProjectDepartment.class);
        example.and().andEqualTo("name",name).andEqualTo("status",1);
        return projectDepartmentDao.selectOneByExample(example);

    }

    @Override
    public List<CompanyProjectDto> getAdminUserProjectDepartment(String id) {
        return projectDepartmentDao.getAdminUserProjectDepartment(id);
    }

    @Override
    public ProjectDepartment getById(String project_department_id) {
        return projectDepartmentDao.selectByPrimaryKey(project_department_id);
    }

    /*根据行业、区域获取项目部证书*/
    @Override
    public PageInfo<ProjectDepartmentCredential> getPDCredentialByTradeManager(String trade_id,Integer status,
                                              String area_code,String type,String level,
                                              Integer pageNum,Integer pageSize){
        if(status==2) {
            if (pageNum != null && pageSize != null) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<ProjectDepartmentCredential> pdcs = new ArrayList<>();
            List<ProjectDepartment> projectDepartments = projectDepartmentDao.getProjectDepartmentByTradeManager(trade_id, area_code);
            for (ProjectDepartment projectDepartment : projectDepartments) {
                List<ProjectDepartmentCredential> pdCredentials = projectDepartmentCredentialDao.getPDCredential(projectDepartment.getId(), status, type, level);
                pdcs.addAll(pdCredentials);
            }
            return new PageInfo<>(pdcs);
        }else {
            if (pageNum != null && pageSize != null) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<ProjectDepartmentCredential> pdcs = new ArrayList<>();
            List<ProjectDepartment> projectDepartments = projectDepartmentDao.getProjectDepartmentByTradeManager(trade_id, area_code);
            for (ProjectDepartment projectDepartment : projectDepartments) {
                List<ProjectDepartmentCredential> pdCredentials = projectDepartmentCredentialDao.getCheckedPDCredential(projectDepartment.getId(), status, type, level);
                pdcs.addAll(pdCredentials);
            }
            return new PageInfo<>(pdcs);
        }
    }
    /*获取区域内项目部*/
    public PageInfo<ProjectDepartment>getProjectDepartmentByDistrict(int status,String area_code,String search,Integer pageNum,Integer pageSize){
        if(pageNum!=null&&pageSize!=null){
            PageHelper.startPage(pageNum, pageSize);
        }
        return new PageInfo<>(projectDepartmentDao.getProjectDepartmentByDistrict(area_code,status,search));
    }
}