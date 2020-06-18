package com.zcyk.dao;

import com.zcyk.entity.District;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository

public interface DistrictDao extends Mapper<District> {

    @Select("select * from district order by code asc")
    @Results(id = "districtCompany" ,value = {
            @Result(property = "company_id",column = "company_id"),
            @Result(property = "company_name",column = "company_id" ,javaType = String.class,one = @One(select = "com.zcyk.dao.CompanyDao.getCompanyName"))})
    List<District> getDistrict();

    @Select("select * from district where company_id = #{company_id}")
    District isDistrictManager(String company_id);
}
