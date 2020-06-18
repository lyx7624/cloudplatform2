package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zcyk.annotation.Phone;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 用户信息表
(User)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {

    private static final long serialVersionUID = 945236720902174069L;
    /**
    * 用户id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    /**
    * 姓名
    */
    //@NotNull(message = "姓名不能为空")
    private String name;
    /**
    * 手机号码
    */
    @Phone
    private String account;
    /**
    * 密码
    */

    //@NotNull(message = "密码不能为空")
    @JsonIgnore
    private String password;
    /**
    * 创建时间
    */

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private Date create_time;
    /**
    * 状态 0 注销 1已认证 2 未认证 3认证失败
    */
    private Integer status;
    /**
    * 用户登录时间
    */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date login_time;
    /**
    * 性别 0女 1男
    */
    private String sex;
    /**
    * 民族
    */


    @NotNull(message = "民族不能为空")
    private String nation;


    /**
     * 签发机关*/
    private String authority;

    /**
     * 有效期*/
    private String valid_time;
    /**
    * 头像url
    */
    private String head_img_url;
    /**
    * 出生日期
    */
    @Past(message = "出生日期必须早于当前")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birth_time;


    @Size(min=18, max=18,message = "请检查名称的长度是否正确（18位）")
    private String id_card;


    @Size(min=1, max=100,message = "请检查名称的长度是否正确（1~100）")
    private String address;

    /**
     * 区域编码
    * */
    @NotNull(message = "请选择区域")
    private String area_code;
    @Transient//市
    private String city;
    @Transient//省
    private String province;

    @Transient
    private String user_credential_id;

    @Transient
//    @NotNull(message = "验证码不能为空")
    private String code;

    @Transient
    private UserProject userProject;


    @Transient
    private List<UserCredential> userCredentials;

    @Transient
    private String role;

    @Transient
    private String auditor;

    @Transient
    private String station_name;

    @Transient
    private String department_name;

    @Transient
    private String superior;


    @Transient
    private String company_id;

    @Transient
    private String ucs_id;

    @Transient
    private String company_name;
    @Transient
    private String company_logo;



}