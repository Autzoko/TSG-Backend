package com.example.tsgbackend.common.exception;

import com.example.tsgbackend.common.utils.ExceptionUtil;
import com.example.tsgbackend.common.utils.ResultUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionConfig extends ResultUtil {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        exception.printStackTrace();
        return fail(false, ExceptionUtil.getStackTraceInfo(exception));
    }
}
