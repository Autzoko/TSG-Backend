package com.example.tsgbackend.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BadRequestException extends RuntimeException{
    private Integer status = HttpStatus.BAD_REQUEST.value();
    private final String msg;

    public BadRequestException(String msg) {
        this.msg = msg;
    }

    public BadRequestException(HttpStatus status, String msg) {
        this.msg = msg;
        this.status = status.value();
    }
}
