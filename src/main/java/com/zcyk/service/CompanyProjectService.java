package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.CompanyProjectDto;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyProject;
import com.zcyk.entity.ProjectDepartment;

import java.util.List;

/**
 * 企业——项目组(CompanyProject)表服务接口
 *
 * @author makejava
 * @since 2020-06-01 11:05:47
 */
public interface CompanyProjectService {


    /*获取企业所有的项目部*/
    PageInfo<CompanyProjectDto> getCompanyProject(String company_id, Integer pageSize, Integer pageNum, String search, int status);
    List<CompanyProject> getCompanyProject(String company_id,String project_department_id);
    /*申请加入项目部*/
    boolean joinCompanyProject(CompanyProject companyProject);

    /*修改*/
    boolean updateCompanyProject(CompanyProject companyProject);

    /*获取项目所有企业*/
    PageInfo<CompanyProject>getCompanyByDepartment(String department_id,Integer status,Integer pageNum,Integer pageSize);


    /*获取群组下的所有公司*/
    PageInfo<CompanyProjectDto> getProjectCompany(Integer pageNum, Integer pageSize, String search, String project_department_id, String status);

    CompanyProjectDto getOneProjectCompany(String id);
    CompanyProject getOneRecord(String id);
}