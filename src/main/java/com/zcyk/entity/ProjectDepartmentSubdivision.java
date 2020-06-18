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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.io.Serializable;

/**
 * 公司——部门(ProjectDepartmentSubdivision)实体类
 *
 * @author makejava
 * @since 2020-06-10 09:25:06
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectDepartmentSubdivision implements Serializable {
    private static final long serialVersionUID = -35847711009385955L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    /**
     * 名称
     */
    @Size(min=2, max=50,message = "请输入位正确部门名称(2~50位)")
    private String name;
    /**
     * 上级部门(项目部下面的一级项目 绑定项目id)
     */
    @NotNull(message = "上级id不能为空")
    private String superior_id;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    /**
     * 创建人
     */
    private String administrator_id;
    /**
     * 状态
     */
    private Integer status;



}