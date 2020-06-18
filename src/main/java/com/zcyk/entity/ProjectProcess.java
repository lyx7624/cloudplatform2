package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目部流程表
 * @author WuJieFeng
 * @date 2020/6/8 15:42
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectProcess implements Serializable {
    private static final long serialVersionUID = 548173537488200660L;
    /**
     * 项目流程id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;
    /*发起人id*/
    private String initiator_id;
    /*发起人项目id*/
    private String initiator_project_id;
    /*审核人id*/
    private String handler_id;
    /*审核人项目id*/
    private String handler_project_id;
    /*流程状态：待审核（1、添加上级、2、添加下级、3、申请退出）、已审核（4、同意加入、5、同意退出、6、拒绝加入、7、拒绝退出、8、撤回 9、踢出））*/
    private Integer status;
    /*流程发起时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date start_time;
    /*流程结束时间*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date end_time;

    @Transient
    private String handler_parent_name;
    @Transient
    private String initiator_project_name;
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
