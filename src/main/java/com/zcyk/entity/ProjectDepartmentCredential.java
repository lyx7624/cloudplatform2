package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zcyk.dto.Credential;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author WuJieFeng
 * @date 2020/6/3 15:52
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class ProjectDepartmentCredential extends Credential {
    private static final long serialVersionUID = -94973969890675187L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;
    /**
     * 项目组id
     */
    private String project_department_id;

    @Transient
    private String project_department_name;
    @Transient
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date create_time;
    @Transient
    private String company_name;
    @Transient
    private Integer user_count;
    @Transient
    private Integer company_count;
}
