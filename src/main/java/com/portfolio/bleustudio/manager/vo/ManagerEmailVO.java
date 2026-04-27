package com.portfolio.bleustudio.manager.vo;

import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ManagerEmailVO {

    private static final int MAX_LENGTH = 100;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final String value;

    private ManagerEmailVO(String value) {
        this.value = value;
    }

    public static ManagerEmailVO from(String rawEmail) {
        validate(rawEmail);

        String normalized = rawEmail.trim().toLowerCase();
        return new ManagerEmailVO(normalized);
    }

    public String getValue() {
        return value;
    }

    private static void validate(String rawEmail) {
        if (rawEmail == null || rawEmail.isBlank()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_EMAIL, "이메일은 필수입니다.");
        }

        String normalized = rawEmail.trim().toLowerCase();

        if (normalized.length() > MAX_LENGTH) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_EMAIL, "이메일은 100자 이하여야 합니다.");
        }

        if (!EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_EMAIL, "이메일 형식이 올바르지 않습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagerEmailVO other)) return false;
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
