package com.zcyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import lombok.experimental.Tolerate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.io.Serializable;

/**
 * 文件存储表(UploadFile)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class UploadFile implements Serializable {

    private static final long serialVersionUID = 328127643552872882L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;
    /**
    * 文件存储路径
    */
    private String path;
    /**
    * 文件大小
    */
    private Long size;
    /**
    * 文件后缀
    */
    private String suffix;
    /**
    * 文件名
    */
    private String name;
    /**
    * 文件md5值
    */
    private String md5;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date update_time;
    /**
    * 文件状态
    */
    private Integer status;
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date create_time;


}