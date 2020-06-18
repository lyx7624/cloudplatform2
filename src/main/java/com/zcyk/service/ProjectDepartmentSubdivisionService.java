package com.zcyk.service;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyDepartment;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.entity.ProjectDepartmentSubdivision;
import com.zcyk.entity.ProjectStation;

import java.util.List;

/**
 * 公司——部门(ProjectDepartmentSubdivision)表服务接口
 *
 * @author makejava
 * @since 2020-06-10 09:25:06
 */
public interface ProjectDepartmentSubdivisionService {


    /*新增*/
    ResultData addDepartmentSubdivision(ProjectDepartmentSubdivision projectDepartmentSubdivision);

    /*获取下级部门*/
    List<ProjectDepartmentSubdivision> getUnderDepartment(String superior_id);



    /*修改部门 根据id*/
    ResultData updateDepartment(ProjectDepartmentSubdivision projectDepartment);

    /*删除部门下级的岗位*/
    void deleteDepartmentAndUnder(String id);
}