package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zcyk.dto.Credential;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.Date;
import java.io.Serializable;

/**
 * 用户证书表(UserCredential)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserCredential extends Credential {

    private static final long serialVersionUID = -18561393898657688L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;
    /**
    * 用户id
    */
    private String user_id;

    @Transient
    private String user_name;
    @Transient
    private String id_card;
    @Transient
    private String sex;
    @Transient
    private String address;
    @Transient
    private String account;




}