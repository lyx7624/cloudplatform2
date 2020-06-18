package com.zcyk.dao;

import com.zcyk.entity.CompanyCredential;
import com.zcyk.entity.UserCredential;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 企业证书(CompanyCredential)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */

public interface CompanyCredentialDao extends Mapper<CompanyCredential>{


    @Select("select cc.*,c.name as company_name,c.code as company_code,c.oper_name,c.create_time,c.type as company_type from  company_credential cc " +
            "inner join company c " +
            "on cc.company_id = c.id" +
            " where cc.company_id = #{company_id} and cc.status = #{status} " +
            "and cc.type like '%${type}%' and cc.level like '%${level}%'")
    @Results(id = "examine",value ={
            @Result(property = "examine_user_name",column = "examine_user_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "trade_name",column = "trade_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.TradeDao.getTradeNameById")),
            @Result(property = "examine_unit_name",column = "examine_unit_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName"))})
    List<CompanyCredential> getByTradeManager(@Param("company_id") String company_id,
                                              @Param("status") Integer status,
                                              @Param("type")String type,
                                              @Param("level")String level);
    /*查询已审核的*/
    @Select("select cc.*,c.name as company_name,c.code as company_code,c.oper_name,c.create_time,c.type as company_type from  company_credential cc " +
            "inner join company c " +
            "on cc.company_id = c.id" +
            " where cc.company_id = #{company_id} and cc.status in (1,3) " +
            "and cc.type like '%${type}%' and cc.level like '%${level}%'")
    @Results(value ={
            @Result(property = "examine_user_name",column = "examine_user_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.UserDao.getNameById")),
            @Result(property = "examine_unit_name",column = "examine_unit_id",javaType = String.class,
                    one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName"))})
    List<CompanyCredential> getCheckedByTradeManager(@Param("company_id") String company_id,
                                              @Param("status") Integer status,
                                              @Param("type")String type,
                                              @Param("level")String level);


    @Select("select cc.*,t.name from  company_credential cc left join trade t on cc.trade_id = t.id where company_id = #{company_id} and status = #{status}")
    @ResultMap("examine")
    List<CompanyCredential> getByCompanyId(@Param("company_id") String company_id, @Param("status")Integer status);

    @Select("select cc.*,t.name from  company_credential cc left join trade t on cc.trade_id = t.id where company_id = #{company_id} and status !=0")
    @ResultMap("examine")
    List<CompanyCredential> getAllByCompanyId(@Param("company_id") String company_id);

    @Select("select * from company_credential where id = #{id}")
    @Results(value = {
            @Result(property = "area_code",column = "area_code",
                    one = @One(select = "com.zcyk.dao.CountyDao.getCountyName")),
            @Result(property = "city",column = "area_code",
                    one = @One(select = "com.zcyk.dao.CountyDao.getHigherName")),
            @Result(property = "trade_name",column = "trade_id",
                    one = @One(select = "com.zcyk.dao.TradeDao.getTradeNameById"))

    })
    CompanyCredential getById(@Param("id")String id);
}