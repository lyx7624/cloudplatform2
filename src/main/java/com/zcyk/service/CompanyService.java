package com.zcyk.service;

import com.github.pagehelper.PageInfo;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UnitprojectGis;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyCredential;

import java.util.List;

/**
 * 企业信息表(Company)表服务接口
 *
 * @author makejava
 * @since 2020-04-28 17:29:16
 */
public interface CompanyService {



    List<Company> getCompanyByUser(String id, int pageNum, int pageSize, String search);

    Company getCompanyById(String id);

    /*查询所有企业*/
    PageInfo<Company> getAllCompany(Integer pageNum, Integer pageSize, String search);

    /*调用企查查接口查企业*/
    ResultData getBycodeForQCC(String code);

    /*获取公司及项目的临时方法*/
    List<UnitprojectGis> getCompanyProject();

    /*行管获取企业证书*/
    PageInfo<CompanyCredential> getCompanyByTradeManager(String trade_id,
                                                         int status,
                                                         String area_code,
                                                         String type,
                                                         String level,
                                                         Integer pageNum,
                                                         Integer pageSize);
    /*区管获取企业列表*/
    PageInfo<Company> getCompanyByDistrictManager(int status,String area_code,String search,Integer pageNum,Integer pageSize);




    /*根据企业编号查询企业*/
    Company getByCode(String code);

    /*查找公司*/
    List<Company> searchCompany(String search);
    /*添加企业*/
    boolean addCompany(Company company);


    /*修改公司信息  也只有修改公司的上级*/
    ResultData updateCompany(Company company);

    /*获取子公司*/
    List<Company> getCompanyBySuperior(String superior_id);


}