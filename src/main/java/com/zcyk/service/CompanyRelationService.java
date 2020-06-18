package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.entity.CompanyRelation;

import java.util.List;

/**
 * 公司关系表(CompanyRelation)表服务接口
 *
 * @author makejava
 * @since 2020-05-29 17:02:45
 */
public interface CompanyRelationService {


    /*获取公司所有关系*/
    PageInfo<CompanyRelation> getAllRelation(String company_id, Integer pageNum, Integer pageSie, String search, Integer status);

    /*添加关系*/
    boolean addRelation(CompanyRelation companyRelation);


    /*撤销上下级申请*/
    ResultData cancelAddRelation(CompanyRelation companyRelation);

    /*修改关系*/
    boolean updateRelationById(CompanyRelation companyRelation);

    /*根据条件查找*/
    CompanyRelation getUpRelation(String company_id);

    PageInfo<CompanyRelation> getByRelation(String relation_id, Integer pageNum, Integer pageSie, String search, int Status);

    PageInfo<CompanyRelation> selectNoCheck(String company_id,Integer pageNum,Integer pageSize,String search);

    PageInfo<CompanyRelation> selectCheck(String company_id,Integer pageNum,Integer pageSize,String search);

    PageInfo<CompanyRelation> selectChecked(String company_id,Integer pageNum,Integer pageSize,String search);

    ResultData updateCompanyRelation(CompanyRelation companyRelation);

    boolean relatedCompany(CompanyRelation companyRelation);
}