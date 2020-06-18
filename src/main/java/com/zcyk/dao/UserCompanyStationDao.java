package com.zcyk.dao;

import com.zcyk.dto.UserStationDto;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyStation;
import com.zcyk.entity.User;
import com.zcyk.entity.UserCompanyStation;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户公司岗位表(UserCompanyStation)表数据库访问层
 *
 * @author makejava
 * @since 2020-05-25 14:40:38
 */
@Repository
public interface UserCompanyStationDao extends Mapper<UserCompanyStation> {


    /*根据用户查询用户企业岗位表*/
    @Select("select ucs.id user_station_id,ucs.status status,ucs.join_time ,c.*,t.name trade from user_company_station ucs " +
            " inner join company c on ucs.company_id = c.id" +
            " left join trade t on t.id = c.trade_id" +
            "   where ucs.user_id = #{user_id} and ucs.status not in (4,0)")
    List<Company> getCompanyByUser(@Param("user_id") String user_id);

    /*根据用户查询用户企业岗位表*/
    @Select("select ucs.id user_station_id,ucs.status status,ucs.join_time,ucs.quit_time ,c.*,t.name trade,ucs.role from user_company_station ucs " +
            " inner join company c on ucs.company_id = c.id" +
            " left join trade t on t.id = c.trade_id" +
            "   where ucs.user_id = #{user_id} and ucs.status not in (4,0)")
    List<Company> getUserManageCompany(@Param("user_id") String user_id);


    /*获取公司的所有人员审核信息*/
    @Select("select ucs.*,u.name  user_name from user_company_station ucs inner join user u on ucs.user_id = u.id  where ucs.company_id = #{company_id} and ucs.status = 2")
    @Results(value = {@Result(property = "in_auditor",column = "in_auditor_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById"))})
    List<UserCompanyStation> gatAuditUser(@Param("company_id") String company_id);



    @Select("select count(*) from user_company_station  where company_id = #{company_id} and status = 1")
    Integer gatAllUserCount(@Param("company_id") String company_id);

    /*获取公司人员信息*/
    @Select("select ucs.id ucs_id,u.*,u.sex,u.name,ucs.role,ucs.status,uds.name station_name,ud.name department_name,ucs.code,ucs.superior_id,c.name company_name from user u "+
            " left join user_company_station ucs on ucs.user_id = u.id"+
            " left join company_station uds on ucs.station_id = uds.id"+
            " left join company_department ud on uds.department_id = ud.id"+
            " left join company c on c.id = #{company_id}"+
            " where ucs.company_id = #{company_id} and u.name like '%${search}%' and ucs.status = #{status}")
    @Results(id= "companyUsers",value ={
            @Result(property = "superior",column = "superior_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById"))
    })
    List<User> gatAllUser(@Param("company_id") String company_id, @Param("search") String search,@Param("status") Integer status);

    /*获取用户历史岗位*/
    @Select("select * from user_company_station where user_id = #{user_id} and company_id = #{company_id} and status = 4")
    @ResultMap("userStation")
    List<UserStationDto> getHistoryStation(@Param("user_id") String user_id,@Param("company_id") String company_id);

    /*获取下级员信息*/
    @Select("select u.*,ucs.status,us.name station_name,ud.name department_name,ucs.code,ucs.superior_id from user_company_station ucs "+
            " left join user u on ucs.user_id = u.id " +
            " left join company_station us on ucs.station_id = us.id " +
            " left join company_department ud on ucs.department_id = ud.id"+
            " where ucs.superior_id = #{user_id} and u.name like '%${search}%' and  ucs.status = 1")
    @Results(value ={
            @Result(property = "superior",column = "superior_id",javaType = String.class,one = @One(select = "com.zcyk.dao.UserDao.getNameById"))
    })
    List<User> gatUserUnder(@Param("search")String search,@Param("user_id") String user_id);

    /*获取下级员信息*/
    @Select("select ucs.role,ucs.company_id,us.name station,c.name company_name,c.logo_url company_logo from user_company_station ucs "+
            " inner join company_station us on ucs.station_id = us.id"+
            " inner join company c on c.id = #{company_id}"+
            " where ucs.user_id = #{user_id} and ucs.company_id = #{company_id} and ucs.status = 1")
    UserCompanyStation getUserCompanyRole(@Param("user_id")String user_id, @Param("company_id")String company_id);

    @Select("select * from user_company_station where id = #{id}")
    @Results(id = "userStation",value ={
            @Result(property = "role",column = "role"),
            @Result(property = "station_id",column = "station_id"),
            @Result(property = "superior_id",column = "superior_id"),
            @Result(property = "user_name",column = "user_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "in_auditor_name",column = "in_auditor_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "out_auditor_name",column = "out_auditor_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "station_name",column = "station_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyStationDao.getStationNameById")),
            @Result(property = "superior_name",column = "superior_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "department_name",column = "station_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyStationDao.getStationDepartmentName"))

    })
    UserStationDto getCompanyStationById(@Param("id") String id);

    @Select("select * from user_company_station where user_id = #{user_id} and status = 1")
    UserCompanyStation selectByUser(@Param("user_id") String user_id);


    @Select("select name from company c inner join user_company_station ucs on ucs.company_id = c.id where ucs.user_id = #{user_id} and ucs.status = 1")
    String selectCompanyNameByUser(@Param("user_id") String user_id);
}