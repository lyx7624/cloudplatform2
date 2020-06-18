package com.zcyk.dao;

import com.zcyk.entity.Trade;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 行业(Trade)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Repository
public interface TradeDao extends Mapper<Trade> {

    @Select("select name from trade where id = #{id}")
    String getTradeNameById(@Param("id")String id);

    @Select("select * from trade order by code asc")
    List<Trade>getTradeAsc();

}
