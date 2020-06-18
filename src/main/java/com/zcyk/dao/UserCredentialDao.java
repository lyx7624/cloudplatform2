package com.zcyk.dao;

import com.zcyk.entity.User;
import com.zcyk.entity.UserCredential;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 用户证书表(UserCredential)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
public interface UserCredentialDao extends Mapper<UserCredential> {

    //根据行业查询证书
    @Select("select uc.*,u.name as user_name,u.id_card,u.sex,u.address,u.account" +
            " from user_credential uc inner join user u on uc.user_id = u.id where " +
            "uc.trade_id = #{trade_id} and uc.area_code = #{area_code} and uc.status = #{status} " +
            "and uc.type like '%${type}%' and uc.level like '%${level}%'")
    @Results(value ={
             @Result(property = "examine_user_name",column = "examine_user_id",javaType = String.class,
                     one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "examine_unit_name",column = "examine_unit_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName"))})
    List<UserCredential> getUserCredentialByTrade(@Param("trade_id") String trade_id, @Param("status") int status,
                                        @Param("area_code")String area_code,
                                        @Param("type")String type, @Param("level")String level);

    @Select("select uc.*,u.name as user_name,u.id_card,u.sex,u.address,u.account" +
            " from user_credential uc inner join user u on uc.user_id = u.id where " +
            "uc.trade_id = #{trade_id} and uc.area_code = #{area_code} and uc.status in (1,3) " +
            "and uc.type like '%${type}%' and uc.level like '%${level}%'")
    @Results(value ={
            @Result(property = "examine_user_name",column = "examine_user_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "examine_unit_name",column = "examine_unit_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName"))})
    List<UserCredential> getCheckedUserCredentialByTrade(@Param("trade_id") String trade_id, @Param("status") int status,
                                                  @Param("area_code")String area_code,
                                                  @Param("type")String type, @Param("level")String level);







//    private String examine_user_name;
//    @Transient
//    private String examine_unit_name;
    @Select("select * from user_credential where user_id = #{user_id} and status != 0")
    @Results(value = {
            @Result(property = "area_code",column = "area_code"),
            @Result(property = "city",column = "area_code",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher")),
            @Result(property = "province",column = "city",javaType = String.class,one = @One(select = "com.zcyk.dao.CountyDao.getHigher")),
            @Result(property = "examine_user_name",column = "examine_user_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "examine_unit_name",column = "examine_unit_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName"))

    })
    List<UserCredential> getByUserId(@Param("user_id") String userId);

    @Select("select * from user_credential where id = #{id}")
    @Results(value = {
            @Result(property = "area_code",column = "area_code",
                    one = @One(select = "com.zcyk.dao.CountyDao.getCountyName")),
            @Result(property = "city",column = "area_code",
                    one = @One(select = "com.zcyk.dao.CountyDao.getHigherName")),
            @Result(property = "trade_name",column = "trade_id",
                    one = @One(select = "com.zcyk.dao.TradeDao.getTradeNameById"))

    })
    UserCredential getById(@Param("id")String id);
}