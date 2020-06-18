package com.zcyk.dto;

import lombok.Data;

/**
 * @author WuJieFeng
 * @date 2020/6/4 10:59
 */
@Data
public class QCCResult {

    private String KeyNo;

    private String Name;

    private String No;
    /*登记机关*/
    private String BeLongOrg;
    /*法人*/
    private String OperName;

    private String StartDate;

    private String EndDate;
    /*存续（在营、开业、在册）*/
    private String Status;
    /*省份*/
    private String Province;
    /*更新日期*/
    private String updateDate;
    /*信用代码*/
    private String CreditCode;
    /*注册资金*/
    private String RegistCapi;
    /*企业类型*/
    private String EconKind;
    /*地址*/
    private String Address;
    /*营业范围*/
    private String Scope;
    /*营业开始时间*/
    private String TermStart;
    /*营业结束时间*/
    private String TeamEnd;
    /*发照日期*/
    private String CheckDate;
    /*组织机构代码*/
    private String OrgNo;

}
