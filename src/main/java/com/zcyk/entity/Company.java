package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 企业信息表(Company)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:15
 */

/**
 * 个人状态 0 注销 1已认证 2 未认证 3认证失败 4 冻结
 * 企业状态 0删除 1正常 2 未认证 3认证失败  4冻结
 * 项目部状态
 *
 * 证书状态
 * 个人证书状态 状态 0删除 1已通过(可用) 2申请中 3不通过 4已撤回
 * 企业证书状态 0删除 1已通过(可用) 2申请中 3不通过
 * 项目证书状态
 * */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company implements Serializable {
    private static final long serialVersionUID = 548173537488200660L;
    /**
    * 企业id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;

    @Size(min=1, max=100,message = "请检查企业名称的长度是否正确（1~100）")
    private String name;
    /**
    * 企业地址
    */
        @Size(min=5, max=100,message = "请检查企业地址的长度是否正确（5~100）")
    private String address;
    /**
    * 企业状态
    */
    //0:已注销 1：已创建 2：未创建
    private Integer status;
    /**
    * 企业创建时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    /**
    * 企业编码
    */
    @Size(min=8, max=20,message = "请输入位正确的编码(8~20位)")
    private String code;
    /**
    * 企业logo存储路径
    */
    private String logo_url;
    /**
    * 营业执照路径
    */
    private String license_url;
    /**
    * 公司行业id
    */
    private String trade_id;

    /**
     * 公司行业类别
     */
    private String type;


    @Transient
    private String trade;

    /**
     * 上级
     */
    private String superior_id;

    /**
     * 公司加入密码
     */
    private String company_join_password;
    /**
     * 个人加入密码
     */
    private String user_join_password;

    private String area_code;

    private String oper_name;

    private String regist_capi;

    private String econ_kind;

    private String be_long_org;

    private String scope;






    /*企业关联人员*/
    @Transient
    List<User> users;

    /*企业证书*/
    @Transient
    List<CompanyCredential> companyCredentials;

    /*企业证书*/
    @Transient
    List<Company> child;

    @Transient
    private String user_station_id;

    /*企业证书*/
    @Transient
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    Date join_time;
    /*企业证书*/
    @Transient
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    Date quit_time;

    @Transient
    private Integer user_count;


    @Transient
    private String role;






}