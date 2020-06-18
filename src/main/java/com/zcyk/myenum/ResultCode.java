package com.zcyk.myenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

/**
* 401 前端弹窗
*
* */

    SUCCESS(200, "操作成功"),

    FAILED(400, "响应失败"),

    VALIDATE_FAILED(402, "参数校验失败"),

    ERROR(500, "服务器内部错误"),

    APIERROR(502,"接口错误"),

    NOT_FIND_ERROR(404,"数据缺失"),

    DISTANCE_LOGIN(415,"异地登录"),

    LOGIN_EXPIRY(414,"登陆过期"),

    SQLERROR(503,"数据库写入错误");


    private int code;

    private String msg;


}