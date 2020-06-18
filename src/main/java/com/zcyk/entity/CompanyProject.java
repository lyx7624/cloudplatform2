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
 * 企业——项目组(CompanyProject)实体类
 *
 * @author makejava
 * @since 2020-06-01 11:05:47
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyProject implements Serializable {
    private static final long serialVersionUID = 520853686994847864L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    /**
    * 项目组id
    */
    @NotNull(message = "请选择项目组")
    private String project_department_id;



    /**
     * 申请人id
     */
    private String proposer_id;

    /**
    * 公司id
    */
    private String company_id;


    /*加入 审核人*/
    private String in_auditor_id;


    /*离开 审核人*/
    private String out_auditor_id;


    /*操作时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operation_time;
    /**
    * 加入时间 默认为申请时间
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
    * 0不在场中 1在场中 2申请中 3申请失败 4退出的 5撤回的
    */
    private Integer status;
    /**
    * 建设方、监理方、创建方.....
    */
    @NotNull(message = "请选择角色")
    private String role;



}