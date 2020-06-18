package com.zcyk.dao;

import com.zcyk.entity.CompanyCredential;
import com.zcyk.entity.ProjectDepartmentCredential;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ProjectDepartmentCredentialDao extends Mapper<ProjectDepartmentCredential> {

   //未审核
   @Select("select pdc.*,pd.name as project_department_name,pd.create_time,pd.* from  project_department_credential pdc" +
           " inner join project_department pd on pdc.project_department_id = pd.id " +
           "where project_department_id = #{project_department_id} and pdc.status = #{status} and " +
           "type like '%${type}%' and level like '%${level}%'")
   @Results(id = "pdc",value ={
           @Result(property = "examine_user_name",column = "examine_user_id",javaType = String.class,
                   one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
           @Result(property = "examine_unit_name",column = "examine_unit_id",javaType = String.class,
                   one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
           @Result(property = "company_name",column = "administrator_id",javaType = String.class,
                   one = @One(select = "com.zcyk.dao.UserCompanyStationDao.selectCompanyNameByUser")),
           @Result(property = "user_count",column = "project_department_id",javaType = Integer.class,
                   one = @One(select = "com.zcyk.dao.UserProjectStationDao.getProjectUserCount")),
           @Result(property = "company_count",column = "project_department_id",javaType = Integer.class,
                   one = @One(select = "com.zcyk.dao.CompanyProjectDao.getProjectCompanyCount"))})
   List<ProjectDepartmentCredential>getPDCredential(@Param("project_department_id") String department_id,
                                                    @Param("status") Integer status,
                                                    @Param("type") String type,
                                                    @Param("level") String level);


   @Select("select pdc.*,pd.name as project_department_name,pd.create_time,pd.* from  project_department_credential pdc" +
           " inner join project_department pd on pdc.project_department_id = pd.id " +
           "where project_department_id = #{project_department_id} and pdc.status in (1,3) and " +
           "type like '%${type}%' and level like '%${level}%'")
  @ResultMap("pdc")
   List<ProjectDepartmentCredential>getCheckedPDCredential(@Param("project_department_id") String department_id,
                                                           @Param("status") Integer status,
                                                           @Param("type") String type,
                                                           @Param("level") String level);

   @Select("select * from  project_department_credential where " +
           "project_department_id = #{project_department_id} and status = #{status}")
   List<ProjectDepartmentCredential> getPDCredentialById(@Param("department_id")String project_department_id,@Param("status") Integer status);

    @Select("select pdc.*,pd.name as project_department_name,pd.create_time,pd.* from  project_department_credential pdc" +
            " inner join project_department pd on pdc.project_department_id = pd.id " +
            "where project_department_id = #{project_department_id} and pdc.status !=0 and " +
            "type like '%${type}%' and level like '%${level}%'")
    @ResultMap("pdc")
    List<ProjectDepartmentCredential> getAllPDCredential(@Param("project_department_id") String department_id, @Param("type") String type,
                              @Param("level") String level);

    @Select("select * from project_department_credential where id = #{id}")
    @Results(value = {
            @Result(property = "area_code",column = "area_code",
                    one = @One(select = "com.zcyk.dao.CountyDao.getCountyName")),
            @Result(property = "city",column = "area_code",
                    one = @One(select = "com.zcyk.dao.CountyDao.getHigherName")),
            @Result(property = "trade_name",column = "trade_id",
                    one = @One(select = "com.zcyk.dao.TradeDao.getTradeNameById"))

    })
    ProjectDepartmentCredential getById(@Param("id")String id);
}
