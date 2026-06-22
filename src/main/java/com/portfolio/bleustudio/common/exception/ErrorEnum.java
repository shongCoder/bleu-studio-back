package com.portfolio.bleustudio.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorEnum {

    /**
     * E****
     * E1*** : 도메인(0 = 공통)
     * E*1** : 에러 위치
     *   0 : REQ  - 요청값 형식/입력 검증
     *   1 : BIZ  - 서비스 도메인 규칙/중복/상태
     *   2 : SYS  - 시스템 내부 오류
     * E**1* ~ E***1 : 번호
     */

    /** E0201 / 500 INTERNAL_SERVER_ERROR - 서버 내부 오류입니다. */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E0201", "서버 내부 오류입니다."),

    /** E0002 / 400 BAD_REQUEST - 잘못된 요청입니다. */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "E0002", "잘못된 요청입니다."),

    /** E0103 / 404 NOT_FOUND - 요청한 정보를 찾을 수 없습니다. */
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "E0103", "요청한 정보를 찾을 수 없습니다."),

    /* Auth */
    /** E1001 / 401 UNAUTHORIZED - 로그인에 실패했습니다. */
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "E1001", "로그인에 실패했습니다."),

    /** E1002 / 401 UNAUTHORIZED - 유효하지 않은 토큰입니다. */
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "E1002", "유효하지 않은 토큰입니다."),

    /** E1003 / 401 UNAUTHORIZED - 토큰이 만료되었습니다. */
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "E1003", "토큰이 만료되었습니다."),

    /** E1004 / 403 FORBIDDEN - 접근 권한이 없습니다. */
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "E1004", "접근 권한이 없습니다."),

    /* Manager */
    /** E2001 / 400 BAD_REQUEST - 아이디 형식이 올바르지 않습니다. */
    INVALID_MANAGER_LOGIN_ID(HttpStatus.BAD_REQUEST, "E2001", "아이디 형식이 올바르지 않습니다."),

    /** E2002 / 400 BAD_REQUEST - 비밀번호 형식이 올바르지 않습니다. */
    INVALID_MANAGER_PASSWORD(HttpStatus.BAD_REQUEST, "E2002", "비밀번호 형식이 올바르지 않습니다."),

    /** E2003 / 400 BAD_REQUEST - 이메일 형식이 올바르지 않습니다. */
    INVALID_MANAGER_EMAIL(HttpStatus.BAD_REQUEST, "E2003", "이메일 형식이 올바르지 않습니다."),

    /** E2004 / 400 BAD_REQUEST - 휴대폰 번호 형식이 올바르지 않습니다. */
    INVALID_MANAGER_PHONE(HttpStatus.BAD_REQUEST, "E2004", "휴대폰 번호 형식이 올바르지 않습니다."),

    /** E2101 / 404 NOT_FOUND - 관리자 정보를 찾을 수 없습니다. */
    MANAGER_NOT_FOUND(HttpStatus.NOT_FOUND, "E2101", "관리자 정보를 찾을 수 없습니다."),

    /** E2102 / 409 CONFLICT - 이미 사용 중인 아이디입니다. */
    DUPLICATE_MANAGER_LOGIN_ID(HttpStatus.CONFLICT, "E2102", "이미 사용 중인 아이디입니다."),

    /** E2103 / 409 CONFLICT - 이미 사용 중인 이메일입니다. */
    DUPLICATE_MANAGER_EMAIL(HttpStatus.CONFLICT, "E2103", "이미 사용 중인 이메일입니다."),

    /** E2104 / 403 FORBIDDEN - 사용 중지된 계정입니다. */
    MANAGER_DISABLED(HttpStatus.FORBIDDEN, "E2104", "사용 중지된 계정입니다."),

    /* Role, ManagerRole */
    /** E3101 / 404 NOT_FOUND - 관리자 권한 정보를 찾을 수 없습니다. */
    MANAGER_ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "E3101", "관리자 권한 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ErrorEnum(HttpStatus httpStatus, String code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
