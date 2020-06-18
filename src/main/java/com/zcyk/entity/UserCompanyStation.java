package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户公司岗位表(UserCompanyStation)实体类
 *
 * @author LYX
 * @since 2020-05-25 14:40:38
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class UserCompanyStation implements Serializable {
    private static final long serialVersionUID = -40255490614404881L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;

    /**
     * 用户ID
     */
    private String user_id;





    /**
     * 岗位ID
     */
    private String station_id;


    /**
     * 公司id
     */
    private String company_id;
    /**
     * 用户——公司角色id
     *
     * admin 管理员 admin_ii 二级管理员 user 用户 superior上级用户进入
     */
    private String role;


    /**
     * 0删除  1在职 2申请中 3未通过 4历史岗位 5撤销 6离职
     */
    private Integer status;

    /*入职 审核人*/
    private String in_auditor_id;

    /*离职职 审核人*/
    private String out_auditor_id;


    /*加入时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date join_time;
    /*离职时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date quit_time;

    /*操作时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operation_time;


    private String code;

    /*上级用户id*/
    private String superior_id;

    /*上级名称 如果岗位没有上级（上级部门）
     *上级负责人就是部门创建人还是先不写*/
    @Transient
    private String superior;

    @Transient
    private String user_name;

    @Transient
    private String company_name;
    @Transient
    private String company_logo;
    @Transient
    private String department_name;

    @Transient
    private String user_sex;

    @Transient
    private String type;



    /*审核人*/
    @Transient
    private String in_auditor;

    /*审核人*/
    @Transient
    private String out_auditor;


    @Transient
    private String station;

    /*行业*/
    @Transient
    private String trade;

    /**
     * 个人加入密码
     */
    @Transient
    private String user_join_password;









}