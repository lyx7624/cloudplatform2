package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dto.CompanyProjectDto;
import com.zcyk.entity.CompanyProject;
import com.zcyk.dao.CompanyProjectDao;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.service.CompanyProjectService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * 企业——项目组(CompanyProject)表服务实现类
 *
 * @author makejava
 * @since 2020-06-01 11:05:47
 */
@Service("companyProjectService")
public class CompanyProjectServiceImpl implements CompanyProjectService {
    @Resource
    private CompanyProjectDao companyProjectDao;


    @Override
    public PageInfo<CompanyProjectDto> getCompanyProject(String company_id, Integer pageSize, Integer pageNum, String search, int status) {
       if(pageNum!=null  && pageSize !=null){
           PageHelper.startPage(pageNum,pageSize);
       }
        return new PageInfo<>(companyProjectDao.getCompanyProject(company_id,search,status));
    }

    @Override
    public PageInfo<CompanyProjectDto> getProjectCompany(Integer pageNum, Integer pageSize, String search, String project_department_id, String status) {
        if(pageNum!=null  && pageSize !=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(companyProjectDao.getProjectCompany(project_department_id,search,status));
    }

    @Override
    public CompanyProjectDto getOneProjectCompany(String id) {
        return companyProjectDao.getOneProjectCompany(id);
    }

    @Override
    public CompanyProject getOneRecord(String id) {
        return companyProjectDao.selectByPrimaryKey(id);
    }

    @Override
    public List<CompanyProject> getCompanyProject(String company_id,String project_department_id) {
        Example example = new Example(CompanyProject.class);
        example.and().andEqualTo("company_id",company_id).andEqualTo("project_department_id",project_department_id)
                .andBetween("status",1,2);
        return companyProjectDao.selectByExample(example);
    }



    @Override
    public boolean joinCompanyProject(CompanyProject companyProject) {
        return companyProjectDao.insertSelective(companyProject)>0;

    }

    @Override
    public boolean updateCompanyProject(CompanyProject companyProject) {
        return companyProjectDao.updateByPrimaryKeySelective(companyProject)>0;
    }

    public PageInfo<CompanyProject>getCompanyByDepartment(String department_id,Integer status,Integer pageNum,Integer pageSize){
        if(pageNum!=null  && pageSize !=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(companyProjectDao.getCompanyByDepartment(department_id,status));
    }

}