package com.zcyk.dao;

import com.zcyk.entity.User;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户信息表
(User)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface UserDao extends Mapper<User> {



    /*根据账号查找用户*/
    @Select("select * from user where account = #{account}")
    User selectByAccount(@Param("account") String account);

    /*根据id查找用户姓名*/
    @Select("select name from user where id = #{id}")
    String getNameById(@Param("id") String id);

    /*u.id in (select user_id from user_company where company_id = #{company_id} and*/
    @Select("select * from user u inner join  user_credential uc on u.id = uc.user_id " +
            "where u.status = 1 and uc.level like '%${level}%' and uc.type like '%${type}%'")
    List<User> getCompanyUserOfCredential
            (@Param("company_id") String companyId,@Param("level") String level,@Param("type") String type);

    @Select("select * from user where id not in (select user_id from user_company where status = 1 and company_id != null or company_id != '') and status = 1 ")
    List<User> getUnCompanyUserOfCredential();

    /*根据区域查询人员*/
    @Select("select * from user where area_code = #{area_code} and status = #{status} and name like '%${search}%'")
    List<User> getDistrictUser(@Param("area_code")String area_code,@Param("status")int status,@Param("search") String search);

    @Select("select * from user where id = #{id}")
    @Results(value = {
            @Result(property = "area_code",column = "area_code"),
            @Result(property = "city",column = "area_code",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher")),
            @Result(property = "province",column = "city",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher"))
    })
    User selectById(String id);
}