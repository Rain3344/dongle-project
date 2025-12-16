package com.dongleproject.exception;

import com.dongleproject.common.R;
import io.swagger.v3.oas.annotations.Hidden;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@Hidden
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public R handleRuntimeException(RuntimeException e) {
        // 在控制台打印异常堆栈信息
        logger.error("拦截器异常: {}", e.getMessage(), e);
        
        // 直接构建符合要求的返回结果
        return R.error().data("result", e.getMessage());
    }
}