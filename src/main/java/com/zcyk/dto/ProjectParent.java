package com.zcyk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 功能描述:项目父类
 * 开发人员: xlyx
 * 创建日期: 2020/4/29 10:20
 */
@Data
@Accessors(chain = true)
public class ProjectParent implements Serializable {

    private static final long serialVersionUID = -94973969890675187L;

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
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date plan_start_time;
    /**
     * 计划结束时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actual_start_time;
    /**
     * 计划开始时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actual_end_time;
}