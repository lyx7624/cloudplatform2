package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zcyk.dto.ProjectParent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 子项目表(UnitProject)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
//@Table(name = "unit_project")
public class UnitProject extends ProjectParent {

    private static final long serialVersionUID = -70932442378238433L;
    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;
    /**
     * 名称
     */
    @Size(min=1, max=250,message = "请检查名称的长度是否正确（1~250）")
    private String name;
    /**
     * 项目级别
     */
    private String level;
    /**
     * 类别
     */
    private String type;
    /**
     * 项目阶段
     */
    private String phase;
    /**
     * 预算  元
     */
    @Min(value = 1,message = "金额大于1")
    private String budget;
    /**
     * 编码
     */
    @Size(min=17,max=17,message = "项目编码为17位")
    private String code;
    /**
     * 简介
     */
    @Size(min=1, max=250,message = "请检查长度是否正确（1~250）")
    private String introduction;
    /**
     * 地址
     */
    @Size(min=5, max=100,message = "请检查名称的长度是否正确（5~100）")
    private String address;
    /**
     * 计划开始时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date plan_start_time;
    /**
     * 计划结束时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date plan_end_time;
    /**
     * 项目图片地址
     */
    private String pic_url;
    /**
     * 0 1
     */
    private Integer status;
    /**
     * 计划开始时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actual_start_time;
    /**
     * 计划开始时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actual_end_time;
    /**
    * 项目id
    */
    private String project_id;

    private String project_name;
    /**
     * 模型路径
     */
    private String bim_path;

    private List<ProjectFile>projectFiles;
}