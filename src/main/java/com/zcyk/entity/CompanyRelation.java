package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * 公司关系表(CompanyRelation)实体类
 *
 * @author makejava
 * @since 2020-05-29 17:02:45
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyRelation implements Serializable {
    private static final long serialVersionUID = 603936040563494349L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    /**
    * 申请单位id
    */
    private String company_id;
    /**
    * 被申请单位id
    */
    @NotNull(message = "没有选择关联企业")
    private String relation_id;
    /**
    * 加入时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date join_time;
    /**
    * 退出时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date quit_time;
    /**
    * 申请人id
    */
    private String applicant_id;
    /**
    * 审核人id
    */
    private String audit_id;
//    /**
//    * 状态 0 删除  1在场中 2申请中 3被拒绝 4不在场 5撤销申请
//    */
//    private Integer status;

    /**
     * 数据状态  0未关联 1已添加
     */
    private Integer status;
    /**
     * 0待审核加入 1已添加  2已拒绝 3已撤回加入 4已踢出 5申请退出 6已拒绝退出 7已退出 8已撤回退出
     */
    private Integer flow_status;
    /**
     * 状态 1.加入申请 2.解除申请
     */
    private Integer applicant_type;
    /**
    * 上下级 1上级 2下级
    */
    @NotNull(message = "没有选择关联关系")
    private Integer type;

    /*操作时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operation_time;

    /**
     * 公司加入密码
     */
    @Transient
    private String company_join_password;

    /**
     * 是否修改
     */
    @Transient
    private Integer  update;
    /**
     * 关联公司
     */
    @Transient
    private String relation_name;

    @Transient
    private String  relation;
    /**
     * 审核人
     */
    @Transient
    private String  audit;
    /**
     * 申请人
     */
    @Transient
    private String  applicant;
    /**
     * 企业名称
     */
    @Transient
    private String  company_name;

    /**
     * 企业编码
     */
    @Transient
    private String  company_code;
    /**
     * 企业所有人员
     */
    @Transient
    private Integer  company_users;
    /**
     * 企业项目数 ：项目部
     */
    @Transient
    private Integer  company_projects;

    /**
     * 企业编码
     */
    @Transient
    private String  company_address;

    @Transient//市
    private String city;

    @Transient//省
    private String province;

    @Transient
    private String c_id;



}