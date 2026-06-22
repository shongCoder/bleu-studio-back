package com.portfolio.bleustudio.auth.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor // 필수
@AllArgsConstructor
@Slf4j
@Builder
public class ManagerTokenRequestDTO {

    @NotBlank(message = "refresh token은 필수입니다.")
    private String refreshToken;

}
