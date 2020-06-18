package com.zcyk.service;

import com.zcyk.dto.ResultData;
import com.zcyk.entity.ProjectProcess;

import javax.annotation.Resource;

/**
 * @author WuJieFeng
 * @date 2020/6/8 15:52
 */
public interface ProjectProcessService {
    /*发起流程*/
    ResultData addProcess(ProjectProcess projectProcess);
    /*处理流程*/
    ResultData handleProcess(ProjectProcess projectProcess);
    /*查询流程*/
    ResultData getProcess(String project_id, Integer type,Integer pageNum,Integer pageSize);//type:1、已添加2、待审核3、已审核
}
