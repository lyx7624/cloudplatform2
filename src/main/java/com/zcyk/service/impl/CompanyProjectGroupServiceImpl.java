package com.zcyk.service.impl;

import com.github.pagehelper.PageHelper;
import com.zcyk.dao.CompanyDao;
import com.zcyk.dao.ProjectDao;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyProjectGroup;
import com.zcyk.dao.CompanyProjectGroupDao;
import com.zcyk.entity.Project;
import com.zcyk.service.CompanyProjectGroupService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 企业——项目组(CompanyProjectGroup)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Service("companyProjectGroupService")
@Transactional
public class CompanyProjectGroupServiceImpl implements CompanyProjectGroupService {


    @Resource
    private CompanyProjectGroupDao companyProjectGroupDao;


    @Resource
    private CompanyDao companyDao;

    @Resource
    private ProjectDao projectDao;


    @Override
    public void updateCompanyToGroup(List<Company> companies, String id) {
        //先删除
        Example example = new Example(CompanyProjectGroup.class);
        example.and().andEqualTo("project_group_id",id);
        companyProjectGroupDao.deleteByExample(example);

        CompanyProjectGroup companyProjectGroup = new CompanyProjectGroup()
                .setJoin_time(new Date())
                .setStatus(1)
                .setProject_group_id(id);
        for (Company company : companies) {
            companyProjectGroupDao.insertSelective(companyProjectGroup.setCompany_id(company.getId()).setId(UUID.randomUUID().toString()));
        }
    }

    @Override
    public List<Company> getByProjectGroupId(String id) {
        Example example = new Example(CompanyProjectGroup.class);
        example.and().andEqualTo("project_group_id",id)
                .andEqualTo("status",1);//看后期需不需要把退出的单位显示出了
        List<CompanyProjectGroup> companyProjectGroups = companyProjectGroupDao.selectByExample(example);
        List<Company> companies = new ArrayList<>();

        companyProjectGroups.forEach(companyProjectGroup -> companies.add(companyDao.selectByPrimaryKey(companyProjectGroup.getCompany_id())));
        return companies;
    }

    @Override
    public List<Project> getByCompanyId(String company_id, Integer pageNum, Integer pageSize,String search) {
        if(null!=pageNum && null!=pageSize){
            PageHelper.startPage(pageNum,pageSize);
        }
        return projectDao.getGroupProjectByCompany(company_id,search);
    }
}