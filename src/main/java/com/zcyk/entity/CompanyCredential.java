package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.zcyk.dto.Credential;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.io.Serializable;

/**
 * 企业证书(CompanyCredential)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyCredential extends Credential{
    private static final long serialVersionUID = 868538264556437672L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    private String id;
    /**
    * 企业id
    */
    private String company_id;

    @Transient
    private String company_name;
    @Transient
    private String company_code;
    @Transient
    private String oper_name;
    @Transient
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date create_time;
    @Transient
    private String company_type;




}