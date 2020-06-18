package com.zcyk.service;

import com.zcyk.entity.Company;
import com.zcyk.entity.Project;

import java.util.List;

/**
 * 企业——项目组(CompanyProjectGroup)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface CompanyProjectGroupService {


    void updateCompanyToGroup(List<Company> companies, String id);


    List<Company> getByProjectGroupId(String id);

    List<Project> getByCompanyId(String company_id, Integer pageNum, Integer pageSize,String search);

}