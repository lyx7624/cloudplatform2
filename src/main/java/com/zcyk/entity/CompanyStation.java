package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 公司部门岗位2(CompanyStation)实体类
 *
 * @author lyx
 * @since 2020-05-25 14:40:05
 */
@Data
@Table(name = "company_station")
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyStation implements Serializable {
    private static final long serialVersionUID = -51917638865938797L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    /*上级*/
    private String superior_id;
    /*状态*/
    private Integer status;
    /*创建时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    /**
    * 创建人
    */
    private String create_user;
    /*名称*/
    @Size(min = 2,max = 50,message = "岗位名称长度应在2~50")
    private String name;

    /**
     * 岗位负责人
     */
    private String administrator_id;

    /**
     * 公司id
     */
    private String company_id;
    /**
     * 部门id
     */
    private String department_id;
    @Transient
    private String department_name;


    @Transient
    private List<CompanyStation> child;




}