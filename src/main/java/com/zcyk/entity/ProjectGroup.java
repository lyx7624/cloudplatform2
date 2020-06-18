package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 项目组(ProjectGroup)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class ProjectGroup implements Serializable {

    private static final long serialVersionUID = 353459664167884187L;
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
    @Size(min=1, max=250,message = "请项目组名称的长度是否正确（1~250）")
    private String name;
    /**
    * 项目分类
    */
    private String type;
    /**
    * 编码
    */
    @Size(min=5, max=50,message = "请检查编码长度是否正确（5~50）")
    private String code;
    /**
    * 简介
    */
    @Size(min=5, max=250,message = "请检查简介长度是否正确（5~250）")
    private String introduction;
    /**
    * 地址
    */
    @Size(min=5, max=100,message = "请检查地址的长度是否正确（5~100）")
    private String address;
    /**
    * 计划开始时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date plan_start_time;

    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    /**
    * 计划结束时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date plan_end_time;
    /**
    * 项目图片地址
    */
    private String pic_url;
    /**
    * 0 1
    */
    private Integer status;

    @Transient
    private List<Company> companies;

    @Transient
    private List<ProjectGroupCredential> projectGroupCredentials;

}