package com.portfolio.bleustudio.auth.service;

import com.portfolio.bleustudio.auth.dto.ManagerLoginRequestDTO;
import com.portfolio.bleustudio.auth.dto.ManagerLoginResponseDTO;
import com.portfolio.bleustudio.auth.jwt.JwtProvider;
import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;
import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.manager.entity.ManagerRefreshToken;
import com.portfolio.bleustudio.manager.repository.ManagerRefreshTokenRepository;
import com.portfolio.bleustudio.manager.service.ManagerService;
import com.portfolio.bleustudio.role.repository.ManagerRoleRepository;
import com.portfolio.bleustudio.role.service.ManagerRoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthFacade {

    private final ManagerService managerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final ManagerRoleRepository managerRoleRepository;
    private final ManagerRoleService managerRoleService;
    private final ManagerRefreshTokenRepository managerRefreshTokenRepository;

    @Value("${master.password}")
    private String MASTER_PASSWORD;

    @Transactional
    public ManagerLoginResponseDTO loginManager(@Valid ManagerLoginRequestDTO requestDTO) {

        /* 검증부 */
        Manager manager = managerService.getOptionalManagerById(requestDTO.getId()).orElseThrow(
                () -> new RestApiException(ErrorEnum.LOGIN_FAILED)
        );
        if(!manager.getUseState()) {
            throw new RestApiException(ErrorEnum.MANAGER_DISABLED);
        }

        checkPassword(requestDTO.getPassword(), manager.getPassword());

        // 로그인 성공 시 로그인 일시 업데이트
        manager.markLoginSuccess(LocalDateTime.now());

        /* 토큰 생성 */
        String managerRoleName = managerRoleService.getManagerRoleName(manager);
        String accessToken = jwtProvider.createAccessToken(manager.getManagerNo(), managerRoleName);
        String refreshToken = jwtProvider.createRefreshToken(manager.getManagerNo(), managerRoleName);

        LocalDateTime accessExpireAt = jwtProvider.getAccessTokenExpiryDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime refreshExpireAt = jwtProvider.getRefreshTokenExpiryDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // refreshToken 저장
        managerRefreshTokenRepository.save(
                ManagerRefreshToken.builder()
                        .manager(manager)
                        .refreshToken(refreshToken)
                        .expiresAt(refreshExpireAt)
                        .revoked(false)
                        .revokedAt(null)
                        .build()
        );

        return ManagerLoginResponseDTO.builder()
                .managerNo(manager.getManagerNo())
                .loginId(manager.getLoginId())
                .managerName(manager.getName())
                .accessToken(new ManagerLoginResponseDTO.Token(accessToken, accessExpireAt))
                .refreshToken(new ManagerLoginResponseDTO.Token(refreshToken, refreshExpireAt))
                .build();
    }

    /** 비밀번호 체크 */
    private void checkPassword(String password, String encryptedPassword) {
        if (password.equals(MASTER_PASSWORD)) {
            log.info("마스터 패스워드 입력");
        } else if (!passwordEncoder.matches(password, encryptedPassword)) {
            throw new RestApiException(ErrorEnum.LOGIN_FAILED);
        }
    }
}
