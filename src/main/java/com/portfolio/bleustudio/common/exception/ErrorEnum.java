package com.portfolio.bleustudio.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {

    /**
     * E1111
     * E1*** : 도메인 (0 = 공통)
     * E11** : 수정 위치
     *   0 : REQ  - 요청값/형식/입력 검증
     *   1 : BIZ  - 서비스/도메인 규칙/중복/상태
     *   2 : SYS  - 시스템 내부 오류
     * E111* ~ E1111 : 세부
     */

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0201", "서버 내부 오류입니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E0002", "잘못된 요청입니다."),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "E0103", "요청한 정보를 찾을 수 없습니다."),

    /* Auth */

    /* Manager */
    INVALID_MANAGER_LOGIN_ID(HttpStatus.BAD_REQUEST, "E2001", "아이디 형식이 올바르지 않습니다."),
    INVALID_MANAGER_PASSWORD(HttpStatus.BAD_REQUEST, "E2002", "비밀번호 형식이 올바르지 않습니다."),
    INVALID_MANAGER_EMAIL(HttpStatus.BAD_REQUEST, "E2003", "이메일 형식이 올바르지 않습니다."),
    INVALID_MANAGER_PHONE(HttpStatus.BAD_REQUEST, "E2004", "휴대폰 번호 형식이 올바르지 않습니다."),
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "E2101", "관리자 정보를 찾을 수 없습니다."),
    DUPLICATE_MANAGER_LOGIN_ID(HttpStatus.CONFLICT, "E2102", "이미 사용 중인 아이디입니다."),
    DUPLICATE_MANAGER_EMAIL(HttpStatus.CONFLICT, "E2103", "이미 사용 중인 이메일입니다.");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorEnum(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
