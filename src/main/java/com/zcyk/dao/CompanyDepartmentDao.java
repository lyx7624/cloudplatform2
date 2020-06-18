package com.zcyk.dao;

import com.zcyk.entity.CompanyDepartment;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 公司——部门(CompanyDepartment)表数据库访问层
 *
 * @author lyx
 * @since 2020-05-25 14:29:04
 */
public interface CompanyDepartmentDao extends Mapper<CompanyDepartment> {
    /*@Results(value = {
            @Result(property = "id",column = "id"),
            @Result(property = "childDepartment",column = "id",javaType = List.class,one = @One(select = "com.zcyk.dao.CompanyDepartmentDao.getDepartmentBySuperior")),
            @Result(property = "childStation",column = "id",javaType = List.class,one = @One(select = "com.zcyk.dao.CompanyStationDao.getStationByDepartment"))
    })*/

    @Select("select * from company_department where superior_id = #{superior_id} and status = 1")
    List<CompanyDepartment> getUnderDepartment(@Param("superior_id") String superior_id);

    /*查询下级岗位和部门*/

    @Select("select * from company_department where superior_id = #{superior_id} and status = 1")
    List<CompanyDepartment> getDepartmentBySuperior(@Param("superior_id") String superior_id);


}