package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zcyk.dto.Credential;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * 项目证书表(ProjectGroupCredential)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectGroupCredential extends Credential{

    private static final long serialVersionUID = -94973969890675187L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;
    /**
    * 项目组id
    */
    private String project_group_id;


}