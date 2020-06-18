package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.CompanyProjectDto;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.entity.ProjectDepartmentCredential;

import java.util.List;

/**
 * 公司——部门(ProjectDepartment)表服务接口
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
public interface ProjectDepartmentService {

    /*创建项目部*/
    boolean addProjectDepartment(ProjectDepartment projectDepartment);

    /*修改项目部*/
    boolean updateProjectDepartment(ProjectDepartment projectDepartment);

    /*获取下级的部门以及岗位*/
    List<ProjectDepartment> getUnderDepartmentStation(String superior_id);

    /*根据名字模糊搜索*/
    List<ProjectDepartment> searchProjectDepartmentByName(String name);

    /*获取项目部  根据名字*/
    ProjectDepartment getProjectDepartmentByName(String name);

    /*获取当前用户所在公司他管理的项目部*/
    List<CompanyProjectDto> getAdminUserProjectDepartment(String id);

    /*根据id获取*/
    ProjectDepartment getById(String project_department_id);
    /*根据行业、区域获取项目部证书*/
    PageInfo<ProjectDepartmentCredential> getPDCredentialByTradeManager(String trade_id, Integer status,
                                                                        String area_code, String type, String level,
                                                                        Integer pageNum, Integer pageSize);
    /*获取区域内项目部*/
    PageInfo<ProjectDepartment>getProjectDepartmentByDistrict(int status,String area_code,String search,Integer pageNum,Integer pageSize);
}