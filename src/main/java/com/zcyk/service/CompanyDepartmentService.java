package com.zcyk.service;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyDepartment;
import java.util.List;

/**
 * 公司——部门(CompanyDepartment)表服务接口
 *
 * @author makejava
 * @since 2020-05-25 14:29:04
 */
public interface CompanyDepartmentService {


    /*获取下级岗位和部门*/
    List<CompanyDepartment> getUnderDepartment(String superior_id);

    /*修改企业部门*/

    ResultData updateCompanyDepartment(CompanyDepartment companyDepartment);
    /*添加企业部门*/

    ResultData addCompanyDepartment(CompanyDepartment setStatus);

    void deleteUnderStation(String id);
}