package com.zcyk.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.zcyk.entity.UserProjectStation;
import lombok.Data;

/**
 * 功能描述:
 * 开发人员: xlyx
 * 创建日期: 2020/6/4 14:37
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserStationDto extends UserProjectStation {

    private String user_name;

    private String station_name;
    /**
     * 入职审核人
     */
    private String in_auditor_name;

    /**
     * 委派人
     */
    private String appoint_name;
    /**
     * 委派公司
     */
    private String company_name;
    private String company_role;

    /**
     * 部门id
     */
    private String department_name;

    /**
     * 上级id
     */
    private String superior_name;
    /**
     * 退出审核人
     */
    private String out_auditor_name;
    /*项目部名称*/
    private String project_department_name;






}