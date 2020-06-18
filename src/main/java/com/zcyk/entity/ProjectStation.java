package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * 公司部门岗位2(ProjectStation)实体类
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class    ProjectStation implements Serializable {
    private static final long serialVersionUID = 949803998656457262L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    /**
    * 上级
    */
    private String superior_id;
    
    private Integer status;
    
    private Date create_time;
    /**
    * 创建人
    */
    private String create_user;
    @NotNull(message = "名称不能为空")
    private String name;
    /**
    * 岗位负责人（）
    */
    private String administrator_id;
    /**
    * 项目部id
    */
    @NotNull(message = "项目部id不能为空")
    private String project_department_id;
    /**
    * 部门id
    */
    private String department_id;


}