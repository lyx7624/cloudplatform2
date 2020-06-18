package com.zcyk.dao;

import com.zcyk.entity.Company;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 企业信息表(Company)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:16
 */
public interface CompanyDao extends Mapper<Company> {


    @Select("select * from company c left join user_company uc on c.id = uc.company_id where c.status = 1 and uc.user_id = #{user_id}" +
            " and (c.name like '%${search}%' or c.type like '%${search}%')")
    List<Company> getUserCompany(@Param("user_id") String userId,@Param("search") String search);

    @Select("select * from company where status = 1 and (name like '%${search}%' or type like '%${search}%')")
    List<Company> getAllCompany(@Param("search")String search);

    @Select("select name from company where id = #{company_id}")
    String getCompanyName(@Param("company_id")String company_id);

    @Select("select *,t.name trade from company c left join trade t on c.trade_id = t.id where c.id = #{company_id}")
    Company getCompanyById(@Param("company_id")String company_id);

    /*查找*/
    @Select("select * from company where status = 1 and " +
            "(name like '%${search}%' or code like '%${search}%')")
    List<Company> searchCompany(@Param("search") String search);



    @Select("select * from company where status = 1 and trade_id = #{trade_id} and area_code = #{area_code}")
    List<Company> getCompanyByTradeManager(@Param("trade_id")String trade_id
                                           ,@Param("area_code")String area_code
                                           );
    @Select("select c.*,c.id as company_id from company c where status = #{status} and area_code = #{address} and name like '%${search}%'")
    @Results(value = {
            @Result(property = "user_count",column = "company_id",javaType = Integer.class,
            one = @One(select = "com.zcyk.dao.UserCompanyStationDao.gatAllUserCount"))
    })
    List<Company> getCompanyByDistrictManager(@Param("status")int status,
                                              @Param("address")String address,
                                              @Param("search")String search);
}