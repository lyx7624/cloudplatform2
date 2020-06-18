package com.zcyk.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zcyk.dao.*;
import com.zcyk.dto.QCCResult;
import com.zcyk.dto.ResultData;
import com.zcyk.dto.UnitprojectGis;
import com.zcyk.dto.UnitprojectGisBim;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyCredential;
import com.zcyk.entity.Project;
import com.zcyk.service.CompanyService;
import com.zcyk.util.HttpClientUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 企业信息表(Company)表服务实现类
 *
 * @author makejava
 * @since 2020-04-28 17:29:16
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyDao companyDao;

    @Resource
    private ProjectDao projectDao;


    @Resource
    private CompanyProjectGroupDao companyProjectGroupDao;

    @Resource
    private CompanyCredentialDao companyCredentialDao;




    /*修改公司信息*/
    @Override
    public ResultData updateCompany(Company company) {
        companyDao.updateByPrimaryKeySelective(company);
        return ResultData.WRITE(200,"修改成功",company.getId());

    }

    /*获取子公司*/
    @Override
    public List<Company> getCompanyBySuperior(String superior_id) {
        Example example = new Example(Company.class);
        example.and().andEqualTo("superior_id",superior_id).andEqualTo("status",1);
        List<Company> companies = companyDao.selectByExample(example);
        companies.forEach(company -> company.setChild(getCompanyBySuperior(company.getId())));
        return companies;
    }

    @Override
    public List<Company> getCompanyByUser(String userId, int pageNum, int pageSize, String search) {
        PageHelper.startPage(pageNum, pageSize);
        return companyDao.getUserCompany(userId,search);
    }

    @Override
    public Company getCompanyById(String id) {
        return companyDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo<Company> getAllCompany(Integer pageNum, Integer pageSize, String search) {
        if(null != pageNum && null != pageSize){
            PageHelper.startPage(pageNum, pageSize);
        }
        List<Company> allCompany = companyDao.getAllCompany(search);
        PageInfo<Company> companyPageInfo = new PageInfo<>(allCompany);
        return  companyPageInfo;
    }

    @Override
    public List<UnitprojectGis> getCompanyProject() {
        List<UnitprojectGis> companies = new ArrayList<>();
        List<Company> allCompany = companyDao.getAllCompany("");
        allCompany.forEach(company1 -> {
            UnitprojectGis company = new UnitprojectGis();
            company.setLabel(company1.getName()).setValue(company1.getId());

            List<Project> groupProjectsByCompany = projectDao.getGroupProjectByCompany(company1.getId(), "");
            List<UnitprojectGisBim> projects = null;
            if(groupProjectsByCompany.size()!=0) {
                projects = new ArrayList<>();
                List<UnitprojectGisBim> finalProjects = projects;
                groupProjectsByCompany.forEach(groupProjectByCompany->{
                    UnitprojectGisBim unitprojectGisBim = new UnitprojectGisBim();
                    unitprojectGisBim.setLabel(groupProjectByCompany.getName()).setValue(groupProjectByCompany.getId());
                    finalProjects.add(unitprojectGisBim);
                });
            }
            companies.add(company.setChildren(projects));

        });
        return companies;
    }

    public ResultData getBycodeForQCC(String code){
        String json = HttpClientUtils.companyGet("keyword="+code);
        JSONObject jsonObject = JSONObject.parseObject(json);
        String status = jsonObject.getString("Status");
        String message = jsonObject.getString("Message");
        if(!status.equals("200")){
          return  ResultData.WRITE(400,message);
        }
        String result = jsonObject.getString("Result");
        QCCResult qccResult = JSONObject.parseObject(result, QCCResult.class);
        // List<QCCResult> qccResults = JSONObject.parseArray(result, QCCResult.class);
       // for (QCCResult qccResult:qccResults){

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = null;
            try {
                date = formatter.parse(qccResult.getStartDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Company company = new Company();
            company.setId(UUID.randomUUID().toString())
                   .setCode(qccResult.getCreditCode())
                   .setOper_name(qccResult.getOperName())
                    .setStatus(0)
                    .setName(qccResult.getName())
                    .setCreate_time(date)
                    .setAddress(qccResult.getAddress())
                    .setBe_long_org(qccResult.getBeLongOrg())
                    .setEcon_kind(qccResult.getEconKind())
                    .setRegist_capi(qccResult.getRegistCapi())
                    .setScope(qccResult.getScope());
            companyDao.insert(company);

        return ResultData.SUCCESS(company);
    }



    /*根据企业编号查询企业*/
    public Company getByCode(String code) {
        return companyDao.selectOne(new Company().setCode(code));
    }

    @Override
    public List<Company> searchCompany(String search) {
        return companyDao.searchCompany(search);
    }

    /*添加企业*/
    @Override
    public boolean addCompany(Company company) {
        return companyDao.updateByPrimaryKeySelective(company)>0;//后面还要加密码！！！.2代表需要审核
    }

    /*行管获取企业列表*/
    public PageInfo<CompanyCredential> getCompanyByTradeManager( String trade_id,
                                                   int status,
                                                   String area_code,
                                                   String type,
                                                   String level,
                                                   Integer pageNum,
                                                   Integer pageSize){
        if(status==2) {
            if (pageNum != null && pageSize != null) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<CompanyCredential> ccs = new ArrayList<>();
            List<Company> companys = companyDao.getCompanyByTradeManager(trade_id, area_code);
            for (Company company : companys) {
                List<CompanyCredential> companyCredentials = companyCredentialDao.getByTradeManager(company.getId(), status, type, level);

                ccs.addAll(companyCredentials);

            }
            return new PageInfo<>(ccs);
        }else {
            if (pageNum != null && pageSize != null) {
                PageHelper.startPage(pageNum, pageSize);
            }
            List<CompanyCredential> ccs = new ArrayList<>();
            List<Company> companys = companyDao.getCompanyByTradeManager(trade_id, area_code);
            for (Company company : companys) {
                List<CompanyCredential> companyCredentials = companyCredentialDao.getCheckedByTradeManager(company.getId(), status, type, level);

                ccs.addAll(companyCredentials);

            }
            return new PageInfo<>(ccs);
        }
    }

    /*区管获取企业列表*/
    public PageInfo<Company> getCompanyByDistrictManager(int status,String area_code,String search,Integer pageNum,Integer pageSize){
        if(pageNum!=null&&pageSize!=null){
            PageHelper.startPage(pageNum,pageSize);
        }
        return new PageInfo<>(companyDao.getCompanyByDistrictManager(status,area_code,search));
    }


}
