package com.zcyk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.Tolerate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 项目文件表(ProjectFile)实体类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Accessors(chain = true)
public class ProjectFile implements Serializable {
    private static final long serialVersionUID = -85082009652724998L;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "select uuid()")
    @Column(name = "id")
    private String id;
    /**
    * 工程和单位工程id都行
    */
    private String project_id;
    /**
    * 文件名
    */
    private String name;
    /**
    * 文件类型 图片 表格 模型
    */
    private String type;
    /**
    * 状态
    */
    private Integer status;
    /**
    * 文件MD5
    */
    private String path;



}