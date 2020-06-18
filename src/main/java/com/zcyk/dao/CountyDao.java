package com.zcyk.dao;

import com.zcyk.entity.County;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;


public interface CountyDao extends Mapper<County> {

    /*results*/
    @Select("select higher from county where credit_code = #{credit_code}")

    String getHigher(@Param("credit_code") String credit_code);
    /*results*/
    @Select("select name from county where credit_code = (select higher from county where credit_code = #{credit_code})")
    String getHigherName(@Param("credit_code") String credit_code);

    /*results*/
    @Select("select name from county where credit_code = #{credit_code}")
    String getCountyName(@Param("credit_code") String credit_code);
}
