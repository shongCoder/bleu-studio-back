package com.portfolio.bleustudio.manager.repository;

import com.portfolio.bleustudio.manager.entity.ManagerRefreshToken;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.portfolio.bleustudio.manager.entity.QManagerRefreshToken.managerRefreshToken;

@RequiredArgsConstructor
public class ManagerRefreshTokenRepositoryImpl implements ManagerRefreshTokenRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<ManagerRefreshToken> getManagerRefreshTokenByRefreshToken(String refreshToken) {
        return Optional.ofNullable(jpaQueryFactory.selectFrom(managerRefreshToken)
                .where(managerRefreshToken.refreshToken.eq(refreshToken))
                .fetchOne());
    }
}
