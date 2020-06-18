package com.zcyk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Transient;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * 证书表(Credential)父类
 *
 * @author makejava
 * @since 2020-04-28 17:29:17
 */
@Data
@Accessors(chain = true)
public class Credential implements Serializable {
    private static final long serialVersionUID = -94973969890675187L;


    /**
     * 证书类别
     */
    @NotNull(message = "证书类别不能为空")
    private String type;
    /**
     * 证书等级
     */
    private String level;
    /**
     * 证书编号 应该是唯一被用的
     */
    @Size(min=5, max=30,message = "请检查证书编号的长度是否有问题（5~30）")
    private String code;
    /**
     * 证书上传图片路径
     */
    private String pic_url;
    /**
     * 过期时间
     */
//    @Future(message = "已过期证书不能使用")
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date expiration_time;


    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date registered_time;

    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date apply_time;
    /**
     * 状态 0失效 1可用（不可修改） 2在审核中  3审核失败 4过期
     */
    private Integer status;

    /**
    * 行业id  现在所有的证书行业为固定的
    * */
    private String trade_id;
    @Transient
    private String trade_name;
    /**
    *
    * 审核人
    * */
    private String examine_user_id;
    /**
    * 审核表
    * */
    private String examine_unit_id;

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
    private String examine_user_name;
    @Transient
    private String examine_unit_name;






}