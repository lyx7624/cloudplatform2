package com.zcyk.dao;

import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.User;
import com.zcyk.entity.UserProjectStation;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户项目岗位表(UserProjectStation)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
public interface UserProjectStationDao extends Mapper<UserProjectStation> {


    /*获取项目组下的所有成员 根据status*/
    @Select("select * from user_project_station where project_department_id = #{project_department_id} and status = #{status}")
    @ResultMap("userStation")
    List<UserStationDto> getProjectUserByStatus(@Param("project_department_id") String projectDepartment_id, @Param("status")  Integer status);


    /*获取项目组 Results*/
    @Select("select count(*) from user_project_station where project_department_id = #{project_department_id} and status = 1")
    Integer getProjectUserCount(@Param("project_department_id") String projectDepartment_id);

    /*获取某个公司项目组在职所有成员 Results*/
    @Select("select count(*) from user_project_station where project_department_id = #{project_department_id} and status = 1 and company_id = #{company_id}")
    Integer getInCompanyProjectUserCount(@Param("project_department_id") String projectDepartment_id,@Param("company_id") String company_id);

    /*获取某个公司项目组撤销的所有成员 Results*/
    @Select("select count(*) from user_project_station where project_department_id = #{project_department_id} and status = 6 and company_id = #{company_id}")
    Integer getUnCompanyProjectUserCount(@Param("project_department_id") String projectDepartment_id,@Param("company_id") String company_id);


    /*获取该公司在这个项目中的所有成员 根据status*/
    @Select("select * from user_project_station where project_department_id = #{project_department_id} and company_id = #{company_id}  and status = #{status}")
    @Results(id = "userStation",value = {
            @Result(property = "user_id",column = "user_id"),
            @Result(property = "id",column = "id"),
            @Result(property = "station_id",column = "station_id"),
            @Result(property = "superior_id",column = "superior_id"),
            @Result(property = "user_name",column = "user_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "station_name",column = "station_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.ProjectStationDao.getStationNameById")),
            @Result(property = "in_auditor_name",column = "in_auditor_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "appoint_name",column = "appoint_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "company_name",column = "company_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "company_role",column = "{company_id=company_id,project_department_id=project_department_id}",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyProjectDao.getCompanyProjectRole")),
            @Result(property = "superior_name",column = "superior_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "department_name",column = "station_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.ProjectStationDao.getStationDepartmentName")),
            @Result(property = "project_department_name",column = "project_department_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "out_auditor_name",column = "out_auditor_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),

    })
    List<UserStationDto> getProjectUserByCompany(@Param("company_id") String company_id, @Param("project_department_id") String project_department_id, @Param("status")  Integer status);


    /*获取可用所有的项目部 Results*/
    @Select("select count(*) from user_project_station  where project_department_id = #{project_id} and status = 1")
    Integer getAllUserCount(@Param("project_id") String project_id);

    /*获取用户所在公司在项目部的的所有岗位*/
    @Select("select * from user_project_station where company_id = #{company_id} and user_id = #{user_id} and status != 0 order by status desc")
    @ResultMap("userStation")
    List<UserStationDto> getUserDelegateStation(@Param("company_id") String company_id,@Param("user_id") String user_id);

    /*获取公司未委派的人员*/
    @Select("select * from user u inner join user_company_station ucs on u.id=ucs.user_id where ucs.company_id = #{company_id} and ucs.status=1 and u.id NOT IN  (select user_id from user_project_station where company_id=#{company_id} and status in (1,2))")
    List<User> gatCompanyNoDelegate(@Param("company_id") String company_id);

    @Select("select * from user_project_station where project_department_id = #{project_department_id} and status = #{status} and user_id=#{user_id}")
    @ResultMap("userStation")
    List<UserStationDto> getUserStation(@Param("project_department_id") String project_department_id,@Param("user_id") String user_id,@Param("status") Integer status);

    @Select("select * from user_project_station where id = #{id}")
    @ResultMap("userStation")
    UserStationDto getUserStationById(String id);
}