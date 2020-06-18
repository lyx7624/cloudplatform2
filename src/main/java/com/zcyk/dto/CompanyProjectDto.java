package com.zcyk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zcyk.entity.Company;
import com.zcyk.entity.CompanyCredential;
import com.zcyk.entity.CompanyProject;
import com.zcyk.entity.UserProjectStation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * 功能描述: 公司  群组返回对象
 * 开发人员: xlyx
 * 创建日期: 2020/6/8 10:23
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyProjectDto extends CompanyProject  {

    /**
     * 部门名称
    * */
    private String project_department_name;
    /**
     * 部门名称
    * */
    private String create_time;

    /**
     * 申请人
     */
    private String proposer_name;
    /*委派人
    *
    * */
    private String appoint_name;
    /*加入 审核人*/
    private String in_auditor_name;
    /**
     * 公司
     */
    private String company_name;
    private Company company;

    /**
     * 退出审核人
     * */
    private String out_auditor_name;

    /**
     * 创建公司
     */
    private String create_company_name;

    /**
     * 项目部所有的企业数
     * */
    private Integer project_company_count;

    /**
     * 项目部所有的委派人员
     * */
    private Integer in_project_user;

    /**
     * 本公司项目部所有的委派人员
     * */
    private Integer in_project_company_user;

    /**
     * 本公司撤离的项目部所有的委派人员
     * */
    private Integer un_project_company_user;

    private List<CompanyCredential> companyCredentials;




}