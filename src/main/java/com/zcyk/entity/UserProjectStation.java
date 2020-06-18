package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户项目岗位表(UserProjectStation)实体类
 *
 * @author makejava
 * @since 2020-06-01 10:59:10
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class UserProjectStation implements Serializable {
    private static final long serialVersionUID = 432098818184945805L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    @NotNull(message = "用户id不能为空")
    private String user_id;


    private String station_id;
    /**
    * 0删除  1在职 2申请中 3未通过  4历史岗位  5撤销 6未在项目
    */
    private Integer status;
    
    private String code;
    /**
    * 加入时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date join_time;
    /**
    * 离开时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date quit_time;
    
    private String project_department_id;
    /**
    * admin 管理员  user 用户
    */
    private String role;
    /**
    * 入职审核人
    */

    private String in_auditor_id;

    /**
     * 委派人
     */

    private String appoint_id;

    /**
     * 委派公司
     */
    @NotNull(message = "委派公司不能为空")
    private String company_id;


    /**
     * 状态 1.加入申请 2.解除申请
     */
    private Integer applicant_type;
    /**
    * 操作时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date operation_time;
    


    /**
    * 上级id
    */
    private String superior_id;
    /**
    * 退出审核人
    */
    private String out_auditor_id;



}