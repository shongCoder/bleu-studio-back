package com.portfolio.bleustudio.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0001", "서버 내부 오류입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E0002", "잘못된 요청입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "E0003", "요청한 정보를 찾을 수 없습니다."),

    /* Auth */

    /* Manager */
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "E2001", "관리자 정보를 찾을 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorEnum(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
