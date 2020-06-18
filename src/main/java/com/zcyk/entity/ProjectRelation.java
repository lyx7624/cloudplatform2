package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目部关系表
 * @author WuJieFeng
 * @date 2020/6/8 15:36
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectRelation implements Serializable {
    private static final long serialVersionUID = 548173537488200660L;
    /**
     * 项目关系id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;
    /*项目部id*/
    private String project_id;
    /*上级项目部id*/
    private String parent_id;
    /*关系生成时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_time;
    /*关系结束时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_time;
    /*对应流程id*/
    private String project_process_id;
    /*关系状态1、存在 2、解除关系*/
    private Integer status;


    @Transient
    private String project_name;
    @Transient
    private String parent_name;
    @Transient
    private String initiator;
    @Transient
    private String handler;
    @Transient
    private Integer project_users;
    @Transient
    private Integer type;
}
