package com.zcyk.dao;

import com.zcyk.entity.CompanyDepartment;
import com.zcyk.entity.ProjectDepartmentSubdivision;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 公司——部门(ProjectDepartmentSubdivision)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-10 09:25:06
 */
public interface ProjectDepartmentSubdivisionDao extends Mapper<ProjectDepartmentSubdivision> {


    /*获取下级部门*/
    @Select("select * from project_department_subdivision where superior_id = #{superior_id} and status =1")
    List<ProjectDepartmentSubdivision> getUnderDepartment(@Param("superior_id") String id);
}