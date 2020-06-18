package com.zcyk.dto;

import com.zcyk.myenum.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 功能描述:
 * 版本信息: Copyright (c)2019
 * 公司信息: 智辰云科
 * 开发人员: lyx
 * 版本日志: 1.0
 * 创建日期: 2019/7/18 16:32
 */
@NoArgsConstructor  //无参构造
@AllArgsConstructor  //全参构造
@Data
public class ResultData implements Serializable {
    /*请求状态*/
    private Integer code;
    /*请求结果*/
    private String msg;
    /*请求数据*/
    private Object data;


    public static ResultData SUCCESS(Object data) {
        return new ResultData(ResultCode.SUCCESS, data);
    }

    public static ResultData SUCCESS() {
        return new ResultData(ResultCode.SUCCESS, null);
    }

    public static ResultData FAILED(Object data) {
        return new ResultData(ResultCode.FAILED, data);
    }

    public static ResultData FAILED() {
        return new ResultData(ResultCode.FAILED, null);
    }


    public static ResultData WRITE(ResultCode resultCode, Object data) {
        return new ResultData(resultCode, data);
    }

    public static ResultData WRITE(ResultCode resultCode) {
        return new ResultData(resultCode,null);
    }

    public static ResultData WRITE(Integer code,String msg,Object data) {
        return new ResultData(code,msg,data);
    }

    public static ResultData WRITE(Integer code,String msg) {
        return new ResultData(code,msg,null);
    }

    public ResultData(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }


}