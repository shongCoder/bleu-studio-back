package com.portfolio.bleustudio.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<ErrorResponse> handleRestApiException(RestApiException e) {
        ErrorEnum error = e.getErrorEnum();

        ErrorResponse response = ErrorResponse.builder()
                .code(error.getCode())
                .message(e.getDetailMessage())
                .build();

        return ResponseEntity.status(error.getHttpStatus()).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .findFirst()
                .map(fieldError -> fieldError.getDefaultMessage())
                .orElse(ErrorEnum.INVALID_INPUT_VALUE.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorEnum.INVALID_INPUT_VALUE.getCode())
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorEnum.INTERNAL_SERVER_ERROR.getCode())
                .message(ErrorEnum.INTERNAL_SERVER_ERROR.getMessage())
                .build();

        return ResponseEntity.status(ErrorEnum.INTERNAL_SERVER_ERROR.getHttpStatus()).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException e) {
        ErrorResponse response = ErrorResponse.builder()
                .code(ErrorEnum.ACCESS_DENIED.getCode())
                .message(ErrorEnum.ACCESS_DENIED.getMessage())
                .build();

        return ResponseEntity.status(ErrorEnum.ACCESS_DENIED.getHttpStatus()).body(response);
    }
}
