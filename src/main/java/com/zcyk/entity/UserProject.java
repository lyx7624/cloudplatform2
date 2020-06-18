package com.zcyk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Transient;
import java.io.Serializable;

/**
 * 公司项目表(UserProject)实体类
 *
 * @author makejava
 * @since 2020-05-06 17:23:03
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class UserProject implements Serializable {
    private static final long serialVersionUID = -51975443766172783L;
    /**
    * 用户ID
    */
    private String user_id;
    /**
    * 公司id
    */
    private String project_id;
    /**
    * 用户——公司角色id
    */
    private String role_id;
    /**
    * 0离职 1在职
    */
    private Integer status;
    
    private String id;
    @Transient
    private String project_name;





}