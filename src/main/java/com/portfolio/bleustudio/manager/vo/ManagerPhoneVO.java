package com.portfolio.bleustudio.manager.vo;

import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;

import java.util.Objects;
import java.util.regex.Pattern;

public final class ManagerPhoneVO {

    private static final Pattern NON_DIGIT_PATTERN = Pattern.compile("\\D");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^01[0-9]\\d{7,8}$");

    private final String value;

    private ManagerPhoneVO(String value) {
        this.value = value;
    }

    public static ManagerPhoneVO from(String rawPhone) {
        validate(rawPhone);

        String normalized = normalize(rawPhone);
        return new ManagerPhoneVO(normalized);
    }

    public String getValue() {
        return value;
    }

    private static void validate(String rawPhone) {
        if (rawPhone == null || rawPhone.isBlank()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_PHONE, "휴대폰 번호는 필수입니다.");
        }

        String normalized = normalize(rawPhone);

        if (!PHONE_PATTERN.matcher(normalized).matches()) {
            throw new RestApiException(ErrorEnum.INVALID_MANAGER_PHONE, "휴대폰 번호 형식이 올바르지 않습니다.");
        }
    }

    private static String normalize(String rawPhone) {
        return NON_DIGIT_PATTERN.matcher(rawPhone).replaceAll("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagerPhoneVO other)) return false;
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
