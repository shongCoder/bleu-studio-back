package com.portfolio.bleustudio.auth.service;

import com.portfolio.bleustudio.auth.dto.ManagerLoginRequestDTO;
import com.portfolio.bleustudio.auth.dto.ManagerLoginResponseDTO;
import com.portfolio.bleustudio.auth.dto.ManagerTokenRequestDTO;
import com.portfolio.bleustudio.auth.dto.ManagerTokenResponseDTO;
import com.portfolio.bleustudio.auth.jwt.JwtProvider;
import com.portfolio.bleustudio.auth.jwt.TokenType;
import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;
import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.manager.entity.ManagerRefreshToken;
import com.portfolio.bleustudio.manager.service.ManagerRefreshTokenService;
import com.portfolio.bleustudio.manager.service.ManagerService;
import com.portfolio.bleustudio.role.service.ManagerRoleService;
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
    private final ManagerRoleService managerRoleService;
    private final ManagerRefreshTokenService managerRefreshTokenService;

    @Value("${master.password}")
    private String MASTER_PASSWORD;

    /** 매니저 로그인 */
    @Transactional
    public ManagerLoginResponseDTO loginManager(ManagerLoginRequestDTO requestDTO) {

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
        String accessToken = jwtProvider.createAccessToken(manager.getManagerNo(), managerRoleName, manager.getLoginId());
        String refreshToken = jwtProvider.createRefreshToken(manager.getManagerNo(), managerRoleName, manager.getLoginId());

        LocalDateTime accessExpireAt = jwtProvider.getAccessTokenExpiryDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        LocalDateTime refreshExpireAt = jwtProvider.getRefreshTokenExpiryDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        // refreshToken 저장
        managerRefreshTokenService.createManagerRefreshToken(manager, refreshToken, refreshExpireAt);

        return ManagerLoginResponseDTO.builder()
                .managerNo(manager.getManagerNo())
                .loginId(manager.getLoginId())
                .managerName(manager.getName())
                .accessToken(new ManagerLoginResponseDTO.Token(accessToken, accessExpireAt))
                .refreshToken(new ManagerLoginResponseDTO.Token(refreshToken, refreshExpireAt))
                .build();
    }

    /** 매니저 로그아웃 */
    @Transactional
    public void logoutManager(ManagerTokenRequestDTO requestDTO) {
        /* 검증부 */
        ManagerRefreshToken findRefreshToken =
                managerRefreshTokenService.getOptionalManagerRefreshToken(requestDTO.getRefreshToken())
                        .orElseThrow(() -> new RestApiException(ErrorEnum.INVALID_TOKEN));

        if (findRefreshToken.getRevoked()) {
            return;
        }

        // 토큰 만료
        findRefreshToken.revoke(LocalDateTime.now());
    }

    /** 매니저 엑세스토큰 갱신 */
    @Transactional
    public ManagerTokenResponseDTO reissueAccessToken(ManagerTokenRequestDTO requestDTO) {

        String refreshToken = requestDTO.getRefreshToken();

        /* 검증부 */
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new RestApiException(ErrorEnum.INVALID_TOKEN);
        }
        if (jwtProvider.getTokenType(refreshToken) != TokenType.REFRESH) {
            throw new RestApiException(ErrorEnum.INVALID_TOKEN);
        }
        ManagerRefreshToken findRefreshToken =
                managerRefreshTokenService.getOptionalManagerRefreshToken(refreshToken).orElseThrow(
                        () -> new RestApiException(ErrorEnum.INVALID_TOKEN)
                );
        if (findRefreshToken.getRevoked()) {
            throw new RestApiException(ErrorEnum.INVALID_TOKEN);
        }
        if (findRefreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            findRefreshToken.revoke(LocalDateTime.now());
            throw new RestApiException(ErrorEnum.TOKEN_EXPIRED);
        }

        // 요청한 토큰의 매니저와 DB 로우 매니저 일치 여부
        Long managerNo = jwtProvider.getUserId(refreshToken);
        if (!findRefreshToken.getManager().getManagerNo().equals(managerNo)) {
            throw new RestApiException(ErrorEnum.INVALID_TOKEN);
        }

        // 사용중지된 매니저 예외
        Manager manager = findRefreshToken.getManager();
        if (!manager.getUseState()) {
            findRefreshToken.revoke(LocalDateTime.now());
            throw new RestApiException(ErrorEnum.MANAGER_DISABLED);
        }

        // 신규 엑세스토큰 발급
        String roleName = managerRoleService.getManagerRoleName(manager);
        String newAccessToken =
                jwtProvider.createAccessToken(
                        manager.getManagerNo(),
                        roleName,
                        manager.getLoginId()
                );
        LocalDateTime accessExpireAt = jwtProvider.getAccessTokenExpiryDate()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return ManagerTokenResponseDTO.builder()
                .accessToken(new ManagerTokenResponseDTO.Token(newAccessToken, accessExpireAt))
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
