package com.portfolio.bleustudio.common.dto;

import com.portfolio.bleustudio.common.exception.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO<T> {

    private final String code;
    private final boolean success;
    private final T data;
    private final boolean error;
    private final boolean status;
    private final String message;

    public static <T> ResponseDTO<T> success(T data, String message) {
        return new ResponseDTO<>("200", true, data, false, true, message);
    }

    public static <T> ResponseDTO<T> fail(String code, String message) {
        return new ResponseDTO<>(code, false, null, true, true, message);
    }

    public static <T> ResponseDTO<T> fail(String code, T data, String message) {
        return new ResponseDTO<>(code, false, data, true, true, message);
    }

    public static <T> ResponseDTO<T> fail(ErrorEnum errorEnum) {
        return new ResponseDTO<>(
                errorEnum.getCode(),
                false,
                null,
                true,
                true,
                errorEnum.getMessage()
        );
    }

}
