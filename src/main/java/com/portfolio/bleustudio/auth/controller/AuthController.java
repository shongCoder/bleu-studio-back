package com.portfolio.bleustudio.auth.controller;

import com.portfolio.bleustudio.auth.dto.ManagerLoginRequestDTO;
import com.portfolio.bleustudio.auth.dto.ManagerLoginResponseDTO;
import com.portfolio.bleustudio.auth.dto.ManagerLogoutRequestDTO;
import com.portfolio.bleustudio.auth.service.AuthFacade;
import com.portfolio.bleustudio.common.dto.ResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthFacade authFacade;

    /**
     * 매니저 로그인
     * @param requestDTO
     * @return
     */
    @PostMapping("/manager/login")
    public ResponseDTO<?> loginManager(@Valid @RequestBody ManagerLoginRequestDTO requestDTO) {

        ManagerLoginResponseDTO response = authFacade.loginManager(requestDTO);

        return ResponseDTO.success(response, "로그인 성공");
    }

    /**
     * 매니저 로그아웃
     * @param requestDTO
     * @return
     */
    @PostMapping("/manager/logout")
    public ResponseDTO<?> logoutManager(@Valid @RequestBody ManagerLogoutRequestDTO requestDTO) {

        authFacade.logoutManager(requestDTO);

        return ResponseDTO.success(null, "로그아웃 성공");
    }
}
