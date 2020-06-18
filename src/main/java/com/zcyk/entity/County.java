package com.zcyk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @author WuJieFeng
 * @date 2020/5/25 17:05
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class County implements Serializable {
    private static final long serialVersionUID = 328127643552872882L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;

    /**
     * 区县名称
     */
    private String name;

    /**
     * 区县编码
     */
    private String credit_code;
    /**
     * 上级名称
     */
    private String higher;
}
