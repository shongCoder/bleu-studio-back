package com.portfolio.bleustudio.manager.vo;

import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ManagerPasswordVO {

    private static final int MIN_LENGTH = 8;
    private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");
    private static final Pattern LOWERCASE_PATTERN = Pattern.compile("[a-z]");
    private static final Pattern DIGIT_PATTERN = Pattern.compile("\\d");
    private static final Pattern SPECIAL_CHARACTER_PATTERN =
            Pattern.compile("[`~!@#$%^&*()\\-_=+\\[{\\]}\\\\|;:'\",<.>/?]");

    private final String value;

    private ManagerPasswordVO(String value) {
        this.value = value;
    }

    public static ManagerPasswordVO from(String rawPassword) {
        validate(rawPassword);
        return new ManagerPasswordVO(rawPassword);
    }

    public String getValue() {
        return value;
    }

    private static void validate(String rawPassword) {
        if (rawPassword == null || rawPassword.isBlank()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_PASSWORD, "비밀번호는 필수입니다.");
        }

        if (rawPassword.length() < MIN_LENGTH) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_PASSWORD, "비밀번호는 8자 이상이어야 합니다.");
        }

        if (!UPPERCASE_PATTERN.matcher(rawPassword).find()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_PASSWORD, "비밀번호는 영문 대문자를 1자 이상 포함해야 합니다.");
        }

        if (!LOWERCASE_PATTERN.matcher(rawPassword).find()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_PASSWORD, "비밀번호는 영문 소문자를 1자 이상 포함해야 합니다.");
        }

        if (!DIGIT_PATTERN.matcher(rawPassword).find()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_PASSWORD, "비밀번호는 숫자를 1자 이상 포함해야 합니다.");
        }

        if (!SPECIAL_CHARACTER_PATTERN.matcher(rawPassword).find()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_PASSWORD, "비밀번호는 특수문자를 1자 이상 포함해야 합니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagerPasswordVO other)) return false;
        return Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ManagerPasswordVO(****)";
    }
}
