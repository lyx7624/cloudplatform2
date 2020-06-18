package com.zcyk.dao;

import com.zcyk.entity.Trade;
import com.zcyk.entity.Trade_company;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author WuJieFeng
 * @date 2020/5/25 14:40
 */
@Repository
public interface TradeCompanyDao extends Mapper<Trade_company> {

    @Select("select c.name as county_name,c.higher,o.id as tc_id,o.bind_time,o.bind_status,o.trade_code,t.id as trade_id,o.company_id as company_id from trade t inner join trade_company o on t.id = o.trade_id " +
            "inner join county c on o.county_id = c.id " +
            "where t.id = #{trade_id} order by c.credit_code")
    @Results(id = "tradeCompany",value = {@Result(property = "company_name",column = "company_name"),
                                          @Result(property = "company_id",column = "company_id"),
                                          @Result(property = "higher",column = "higher"),
                                          @Result(property = "tc_id",column = "tc_id"),
                                          @Result(property = "bind_time",column = "bind_time"),
                                          @Result(property = "bind_status",column = "bind_status"),
                                          @Result(property = "trade_code",column = "trade_code"),
                                          @Result(property = "trade_id",column = "trade_id"),
            @Result(property = "company_name",column = "company_id" ,javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName"))})
    List<Trade> selectbind(@Param("trade_id") String trade_id);

    /*判断行业管理员*/
    @Select("select * from trade_company where company_id = #{company_id}")
    Trade_company isTradeManager(String company_id);
}
