package com.zcyk.dao;

import com.fasterxml.jackson.databind.JavaType;
import com.zcyk.dto.CompanyProjectDto;
import com.zcyk.entity.CompanyDepartment;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.entity.ProjectStation;
import com.zcyk.entity.UserProjectStation;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 公司——部门(ProjectDepartment)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
public interface ProjectDepartmentDao extends Mapper<ProjectDepartment> {


    /*查询下级的部门以及岗位*/
    @Select("select * from project_department where superior_id = #{superior_id} and status = 1")
    @Results(value = {
            @Result(property = "id",column = "id"),
            @Result(property = "childDepartment",column = "id",javaType = List.class,one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getDepartmentBySuperior")),
            @Result(property = "childStation",column = "id",javaType = List.class,one = @One(select = "com.zcyk.dao.ProjectStationDao.getStationByDepartment"))
    })
    List<ProjectDepartment> getUnderDepartmentStation(@Param("superior_id") String superior_id);

    /*根据上级查询下级的部门*/
    @Select("select * from project_department where superior_id = #{superior_id} and status = 1")
    List<ProjectDepartment> getDepartmentBySuperior(@Param("superior_id") String superior_id);

    @Select("select * from project_department where id = #{id}")
    @Results(value = {
            @Result(property = "departmentUser",column = "id",javaType = UserProjectStation.class,
            one = @One(select = "com.zcyk.dao.UserProjectStationDao.getProjectUser"))
    })
    ProjectDepartment selectById(@Param("id") String id);

    @Select("select name from project_department where id = #{id}")
    ProjectDepartment selectNmaeById(@Param("id") String id);

    @Select("select * from project_department where name like '%${name}%' and status = 1")
    List<ProjectDepartment> searchProjectDepartmentByName(@Param("name")String name);

    @Select("select name from project_department where id = #{0}")
    String getNameById(String id);

    @Select("select ucs.company_id,ups.*,pd.create_company_id from user_company_station ucs " +
            " inner join user_project_station ups on ups.user_id = ucs.user_id  " +
            " inner join project_department pd on pd.id = ups.project_department_id  " +
            " where ucs.user_id = #{user_id} and ups.company_id = ucs.company_id and ucs.status = 1 and ups.status = 1")
    @Results(value = {
            @Result(property = "project_department_name",column = "project_department_id",
                    one = @One(select = "com.zcyk.dao.ProjectDepartmentDao.getNameById")),
            @Result(property = "project_department_id",column = "project_department_id"),
            @Result(property = "create_time",column = "create_time"),
            @Result(property = "company_name",column = "company_id",
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "create_company_name",column = "create_company_id",
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "project_company_count",column = "project_department_id",
                    one = @One(select = "com.zcyk.dao.CompanyProjectDao.getProjectCompanyCount")),
            @Result(property = "in_project_user",column = "project_department_id",
                    one = @One(select = "com.zcyk.dao.UserProjectStationDao.getProjectUserCount")),
            @Result(property = "in_project_company_user",column = "{project_department_id=project_department_id,company_id=company_id}",
                    one = @One(select = "com.zcyk.dao.UserProjectStationDao.getInCompanyProjectUserCount")),
            @Result(property = "un_project_company_user",column = "{project_department_id=project_department_id,company_id=company_id}",
                    one = @One(select = "com.zcyk.dao.UserProjectStationDao.getUnCompanyProjectUserCount")),
            @Result(property = "out_auditor_name",column = "out_auditor_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "appoint_name",column = "appoint_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "in_auditor_name",column = "in_auditor_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "proposer_name",column = "proposer_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById"))
    })
    List<CompanyProjectDto> getAdminUserProjectDepartment(@Param("user_id") String id);


    @Select("select * from project_department where status = 1 and trade_id=#{trade_id} and area_code=#{area_code}")
    List<ProjectDepartment> getProjectDepartmentByTradeManager(@Param("trade_id") String trade_id,
                                                               @Param("area_code") String area_code);

    @Select("select pd.*,pd.id as project_department_id from project_department pd where status = #{status} and area_code=#{area_code} and name like '%${search}%'")
    @Results(value = {
            @Result(property = "user_count",column = "project_department_id",javaType = Integer.class,
                    one = @One(select = "com.zcyk.dao.UserProjectStationDao.getProjectUserCount")),
            @Result(property = "company_count",column = "project_department_id",javaType = Integer.class,
                    one = @One(select = "com.zcyk.dao.CompanyProjectDao.getProjectCompanyCount")),
            @Result(property = "create_company_name",column = "create_company_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "administrator_name",column = "administrator_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "trade_name",column = "trade_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.TradeDao.getTradeNameById")),
            @Result(property = "area_name",column = "area_code",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CountyDao.getCountyName"))})
    List<ProjectDepartment>getProjectDepartmentByDistrict(@Param("area_code")String area_code,
                                                          @Param("status")Integer status,
                                                          @Param("search")String search);
}