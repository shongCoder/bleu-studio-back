package com.portfolio.bleustudio.common.exception;

import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException {

    private final ErrorEnum errorEnum;

    private final String detailMessage;

    public RestApiException(ErrorEnum errorEnum) {
        super(errorEnum.getMessage());
        this.errorEnum = errorEnum;
        this.detailMessage = errorEnum.getMessage();
    }

    public RestApiException(ErrorEnum errorEnum, String detailMessage) {
        super(detailMessage);
        this.errorEnum = errorEnum;
        this.detailMessage = detailMessage;
    }

    public ErrorEnum getErrorEnum() {
        return errorEnum;
    }

    public String getDetailMessage() {
        return detailMessage;
    }
}
