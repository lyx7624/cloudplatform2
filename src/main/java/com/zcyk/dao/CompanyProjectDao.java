package com.zcyk.dao;

import com.zcyk.dto.CompanyProjectDto;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyProject;
import com.zcyk.entity.ProjectDepartment;
import com.zcyk.entity.UserProjectStation;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 企业——项目组(CompanyProject)表数据库访问层
 *
 * @author makejava
 * @since 2020-06-01 11:05:47
 */
public interface CompanyProjectDao extends Mapper<CompanyProject> {


    /*获取公司所有正在施工 项目数量 Results*/
    @Select("select count(*) from company_project where company_id = #{company_id} and status = 1")
    Integer getCompanyProjectCount(@Param("company_id") String company_id);

    /*获取公司所有正在施工 项目数量 Results*/
    @Select("select role from company_project where company_id = #{company_id} and status = 1 and project_department_id = #{project_department_id}")
    String getCompanyProjectRole(@Param("company_id") String company_id,@Param("project_department_id") String project_department_id);


    @Select("select * from company_project cp inner join project_department pd on cp.project_department_id = pd.id" +
            " where cp.company_id = #{company_id} and cp.status = #{status} and pd.name like '%${search}%'")
    @Results(value = {
            @Result(property = "project_department_name",column = "name"),
            @Result(property = "project_department_id",column = "project_department_id"),
            @Result(property = "create_time",column = "create_time"),
            @Result(property = "create_company_name",column = "crate_company_id",
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
            @Result(property = "in_auditor_name",column = "in_auditor_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "proposer_name",column = "proposer_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById"))
    })
    List<CompanyProjectDto> getCompanyProject(@Param("company_id") String company_id, @Param("search") String search, @Param("status")  int status);

    /*查询该项目所有公司的count Results*/
    @Select("select count(*) from company_project where project_department_id = #{project_department_id} and status = 1")
    Integer getProjectCompanyCount(@Param("project_department_id") String project_department_id);


    @Select("select * from company_project where project_department_id = #{department_id} and status = #{status}")
    @Results(value = {
            @Result(property = "company_name",column = "company_id",
                    javaType = Company.class,one = @One(select = "com.zcyk.Dao.CompanyDao.getCompanyName"))})
    List<CompanyProject>getCompanyByDepartment(@Param("department_id")String department_id,
                                               @Param("status")Integer status);

    /*获取所在项目部的所有公司*/
    @Select("select * from company_project cp inner join company c on cp.company_id = c.id" +
            " where cp.project_department_id = #{project_department_id} and cp.status = #{status} and c.name like '%${search}%'")
    @Results(value = {
            @Result(property = "company_name",column = "company_id",
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName")),
            @Result(property = "in_project_company_user",column = "{project_department_id=project_department_id,company_id=company_id}",
                    one = @One(select = "com.zcyk.dao.UserProjectStationDao.getInCompanyProjectUserCount")),
            @Result(property = "un_project_company_user",column = "{project_department_id=project_department_id,company_id=company_id}",
                    one = @One(select = "com.zcyk.dao.UserProjectStationDao.getUnCompanyProjectUserCount")),
            @Result(property = "out_auditor_name",column = "out_auditor_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "in_auditor_name",column = "in_auditor_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "proposer_name",column = "proposer_id",
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById"))
    })
    List<CompanyProjectDto> getProjectCompany(@Param("project_department_id") String project_department_id,
                                              @Param("search") String search,
                                              @Param("status") String status);

    /*根据id获取公司信息 查看 修改*/
    @Select("select cp.id,cp.company_id,c.name company_name,cp.role,cp.project_department_id from company_project cp inner join company c on cp.company_id = c.id" +
            " where cp.id = #{id}")
    @Results(value = {
            @Result(property = "company",column = "company_id",javaType = Company.class,
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyById")),
            @Result(property = "companyCredentials",column = "company_id",
                    one = @One(select = "com.zcyk.dao.CompanyCredentialDao.getByCompanyId"))
    })
    CompanyProjectDto getOneProjectCompany(@Param("id") String id);
}