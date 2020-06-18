package com.zcyk.dao;

import com.zcyk.entity.CompanyStation;
import com.zcyk.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 公司部门岗位2(CompanyStation)表数据库访问层
 *
 * @author lyx
 * @since 2020-05-25 14:40:05
 */
public interface CompanyStationDao extends Mapper<CompanyStation> {


    /*获取岗位负责人*/
    @Select("select name,id from user where id = (select administrator_id from company_station where id = #{id})")
    User getStationAdministrator(@Param("id") String id);

    /*获取岗位部门  result*/
    @Select("select cd.name from company_station cs inner join company_department cd on cs.department_id = cd.id" +
            " where cs.id = #{id}")
    String getStationDepartmentName(@Param("id") String id);

    /*获取岗位负责人 result*/
    @Select("select name from company_station where id =  #{id}")
    String getStationNameById(@Param("id") String id);

    @Select("select name from user where id = (select administrator_id from company_station where id = #{id})")
    String getStationAdministratorName(@Param("id") String id);

    @Select("select * from company_station where department_id = #{department_id} and status = 1")
    List<CompanyStation> getStationByDepartment(@Param("department_id") String department_id);

    @Select("select * from company_station where superior_id = #{superior_id} and status = 1")
    List<CompanyStation> getStationBySuperior(@Param("superior_id") String superior_id);

    @Select("select cs.*,cd.name department_name from company_station cs left join company_department cd on cd.id = cs.department_id where cs.company_id = #{company_id} and cs.status = #{status}")
    List<CompanyStation> selectByCompanyId(@Param("company_id")String company_id,@Param("status") int status);


}