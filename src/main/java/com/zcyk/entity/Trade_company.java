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
import java.util.List;

/**
 * 行业单位表
 * @author WuJieFeng
 * @date 2020/5/25 13:56
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class Trade_company implements Serializable {
    private static final long serialVersionUID = 328127643552872882L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;

    /**
     * 行业id
     */
    private String trade_id;

    /**
     * 单位id
     */
    private String company_id;

    /**
     * 区县id
     */
    private String county_id;

    /**
     * 绑定状态
     */
    private Integer bind_status;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date bind_time;

    private String trade_code;

    @Transient
    private String password;

    @Transient
    private String company_name;
   @Transient
    private String company_logo;
   @Transient
    private String trade_name;






}
