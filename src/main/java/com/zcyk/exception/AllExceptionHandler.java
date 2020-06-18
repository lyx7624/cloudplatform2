package com.zcyk.exception;

import com.zcyk.dto.ResultData;
import com.zcyk.myenum.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

/**
 * @author WuJieFeng
 * @date 2019/10/17 14:40
 */
@Slf4j
@ControllerAdvice
@ResponseBody
public class AllExceptionHandler {

    /*数据校验异常*/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultData MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return ResultData.WRITE(402,"参数校验失败："+objectError.getDefaultMessage());
    }

    /*请求接口异常*/
    @ExceptionHandler(APIException.class)
    public ResultData APIExceptionHandler(APIException e) {
        return new ResultData(ResultCode.APIERROR,e.getMessage());
    }

    @ExceptionHandler(value = FileNotFoundException.class)
    public ResultData fileExceptionHandler(HttpServletRequest request, Exception e){
        log.error("文件缺失",e);
        return ResultData.WRITE(404,"数据缺失："+e.getMessage());

    }

    @ExceptionHandler(value = Exception.class)
    public ResultData ExceptionHandler(HttpServletRequest request, Exception e){
        log.error(e.getMessage(),e);
        return ResultData.WRITE(400,e.getMessage());

    }


    @ExceptionHandler(value = BadSqlGrammarException.class)
    public ResultData badSqlHandler(HttpServletRequest request, Exception e){
        log.error("数据库语句错误",e);
        return ResultData.WRITE(503,"数据库写入错误："+e.getMessage());

    }


}
