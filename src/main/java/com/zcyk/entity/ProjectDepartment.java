package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 公司——部门(ProjectDepartment)实体类
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDepartment implements Serializable {
    private static final long serialVersionUID = 288006558477688193L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    /**
    * 名称
    */
    @NotNull(message = "请输入名称")
    private String name;
    /**
    * 上级部门(项目部下面的一级项目 绑定项目id)
    */
    private String superior_id;
    /**
    * 创建时间
    */
    private Date create_time;

    /**
     * 创建公司id
     */
    @NotNull(message = "选择创建公司")
    private String create_company_id;
    /**
     * 创建公司id
     */
    @Transient
    private String crate_company;
    /**
    * 创建人
    */
    private String administrator_id;
    /**
     * 区域代码
     */
//    private String area_code;
    /**
     * 创建人
     */
    @Transient
    private String administrator;
    /**
    * 状态
    */
    private Integer status;

    /**
     * 文件路径
     */
    private String logo_url;

    /**
     * 行业id
     */
    private String trade_id;
    /**
     * 区域代码
     */
    private String area_code;

    @Transient
    private List<ProjectDepartment> childDepartment;

    @Transient
    private List<UserProjectStation> departmentUser;


    @Transient
    private List<ProjectStation> childStation;

    @Transient
    private Integer user_count;
    @Transient
    private Integer company_count;
    @Transient
    private String create_company_name;
    @Transient
    private String administrator_name;
    @Transient
    private String trade_name;
    @Transient
    private String area_name;






}