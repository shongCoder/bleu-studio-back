package com.portfolio.bleustudio.manager.controller;

import com.portfolio.bleustudio.auth.annotation.AccessLevel;
import com.portfolio.bleustudio.auth.enums.AccessLevelEnum;
import com.portfolio.bleustudio.auth.enums.AuthLevel;
import com.portfolio.bleustudio.common.dto.ResponseDTO;
import com.portfolio.bleustudio.manager.dto.ManagerSignUpRequestDTO;
import com.portfolio.bleustudio.manager.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/manager")
public class ManagerController {

    private final ManagerService managerService;

    @PreAuthorize("hasRole('SYSTEM')")
    @PostMapping("")
    public ResponseDTO<Long> createManager(@Valid @RequestBody ManagerSignUpRequestDTO requestDTO) {

        Long managerNo = managerService.createManager(requestDTO);

        return ResponseDTO.success(managerNo, "관리자 생성 성공");
    }

}
