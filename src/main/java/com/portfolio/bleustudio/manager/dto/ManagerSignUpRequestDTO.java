package com.portfolio.bleustudio.manager.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

@Data
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor // 필수
@AllArgsConstructor
@Slf4j
@Builder
public class ManagerSignUpRequestDTO {

    @NotBlank(message = "아이디는 필수입니다.")
    private String loginId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @NotBlank(message = "관리자명은 필수입니다.")
    private String name;

    @NotBlank(message = "연락처는 필수입니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

}
