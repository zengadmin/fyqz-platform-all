package com.fyqz.exception;

import com.fyqz.result.Result;
import com.fyqz.result.ResultUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author zengchao
 */
@RestControllerAdvice
public class UserExceptionHandler {
    /**
     * 处理业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result handlerBusinessException(BusinessException e) {
        return ResultUtil.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public Result handlerException(BusinessException e) {
        return ResultUtil.error();
    }
}