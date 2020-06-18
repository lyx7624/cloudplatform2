package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 行业表
 * @author WuJieFeng
 * @date 2020/5/25 13:42
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class Trade implements Serializable {
    private static final long serialVersionUID = 328127643552872882L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;

    /**
     * 行业名称
     */
    @NotNull(message = "行业名称不能为空")
    private String name;

    /**
     * 创建时间
     */

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date create_time;

    /**
     * 行业编码
     */
    private String code;

    /**
     * 菜单信息
     */

    private String menu;

    private String password;



    @Transient
    private String tc_id;

    @Transient
    private String trade_id;

    @Transient
    private String company_id;

    @Transient
    private String county_name;

    @Transient
    private Integer bind_status;

    @Transient
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date bind_time;

    @Transient
    private String higher;

    @Transient
    private String trade_code;

    @Transient
    private String company_name;

//    private Company  company;
    @Transient
    private List<Trade> children;




}
