package com.easyshare.exception;

import com.easyshare.pojo.common.Result;
import com.easyshare.pojo.common.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception ex) {
        return new Result(ResultCode.ERROR.getCode(), "程序异常，请重试", null);
    }
}
