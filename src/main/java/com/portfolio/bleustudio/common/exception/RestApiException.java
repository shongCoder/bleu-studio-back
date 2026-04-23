package com.portfolio.bleustudio.common.exception;

import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {

    private final ErrorEnum errorEnum;

    public RestApiException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
    }
}
