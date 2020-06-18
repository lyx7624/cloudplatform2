package com.zcyk.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author WuJieFeng
 * @date 2020/6/8 11:32
 */
@Entity
@Data
@Table(name = "company_process")
@Accessors(chain = true)
public class CompanyProcess {

    private String id;

    /*发起人*/
    private String initiator;
    /*发起人单位*/
    private String initiator_company_id;
    /*处理人*/
    private String handler;
    /*处理人单位*/
    private String handler_company_id;
    /*流程状态*/
    /**
     * 待审核（1、添加上级、2、添加下级、3、申请退出）、已审核（4、同意加入、5、同意退出、6、拒绝加入、7、拒绝退出、8、撤回）
     */
    private Integer status;
//    /*流程信息*/
//    private String msg;

}
