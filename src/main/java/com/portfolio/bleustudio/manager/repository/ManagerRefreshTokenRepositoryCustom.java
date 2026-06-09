package com.portfolio.bleustudio.manager.repository;

import com.portfolio.bleustudio.manager.entity.ManagerRefreshToken;

import java.util.Optional;

public interface ManagerRefreshTokenRepositoryCustom {

    Optional<ManagerRefreshToken> getManagerRefreshTokenByRefreshToken(String refreshToken);
}
