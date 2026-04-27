package com.portfolio.bleustudio.manager.vo;

import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ManagerLoginIdVO {

    private static final int MIN_LENGTH = 4;
    private static final int MAX_LENGTH = 20;
    private static final Pattern LOGIN_ID_PATTERN = Pattern.compile("^[a-z0-9_-]+$");

    private final String value;

    private ManagerLoginIdVO(String value) {
        this.value = value;
    }

    public static ManagerLoginIdVO from(String rawLoginId) {
        validate(rawLoginId);

        String normalized = rawLoginId.trim();
        return new ManagerLoginIdVO(normalized);
    }

    public String getValue() {
        return value;
    }

    private static void validate(String rawLoginId) {
        if (rawLoginId == null || rawLoginId.isBlank()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_LOGIN_ID, "관리자 아이디는 필수입니다.");
        }

        String normalized = rawLoginId.trim();

        if (normalized.length() < MIN_LENGTH || normalized.length() > MAX_LENGTH) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_LOGIN_ID, "관리자 아이디는 4자 이상, 20자 이하여야 합니다.");
        }

        if (!LOGIN_ID_PATTERN.matcher(normalized).matches()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_LOGIN_ID, "관리자 아이디는 영문 소문자, 숫자, _, -만 사용할 수 있습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagerLoginIdVO other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
