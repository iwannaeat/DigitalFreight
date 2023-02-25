package com.nccbc.digitalfreight.response;

import lombok.extern.slf4j.Slf4j;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@SuppressWarnings("ALL")
@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Log logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(value = BaseException.class)
    public Result baseExceptionHandler(BaseException e){
        logger.error("业务异常原因：" + e.getMessage());

        return Result.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(value = NullPointerException.class)
    public Result exceptionHandler(NullPointerException e){
        logger.error("空指针异常原因：" + e.getMessage());

        return Result.error(BaseErrorEnum.BODY_NOT_MATCH);
    }

    @ExceptionHandler(value = Exception.class)
    public Result exceptionHandler(Exception e){
        logger.error("未知异常原因：" + e);

        return Result.error(BaseErrorEnum.INTERNAL_SERVER_ERROR);
    }
}
