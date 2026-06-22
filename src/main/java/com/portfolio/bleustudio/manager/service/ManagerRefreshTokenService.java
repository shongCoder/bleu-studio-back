package com.portfolio.bleustudio.manager.service;

import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;
import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.manager.entity.ManagerRefreshToken;
import com.portfolio.bleustudio.manager.repository.ManagerRefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class ManagerRefreshTokenService {

    private final ManagerRefreshTokenRepository managerRefreshTokenRepository;

    @Transactional(readOnly = true)
    public Optional<ManagerRefreshToken> getOptionalManagerRefreshToken(String refreshToken) {
        return managerRefreshTokenRepository.getManagerRefreshTokenByRefreshToken(refreshToken);
    }

    @Transactional
    public void createManagerRefreshToken(Manager manager, String refreshToken, LocalDateTime refreshExpireAt) {
        managerRefreshTokenRepository.save(
                ManagerRefreshToken.builder()
                        .manager(manager)
                        .refreshToken(refreshToken)
                        .expiresAt(refreshExpireAt)
                        .revoked(false)
                        .revokedAt(null)
                        .build()
        );
    }

}
