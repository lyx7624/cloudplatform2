package com.zcyk.exception;

/**
 * 功能描述: 接口异常
 * 开发人员: xlyx
 * 创建日期: 2020/1/13 16:09
 */
public class APIException extends RuntimeException {

    public APIException(){}

    public APIException(String str){super(str);}
}