package com.portfolio.bleustudio.manager.repository;

import com.portfolio.bleustudio.manager.entity.ManagerRefreshToken;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ManagerRefreshTokenRepositoryImpl implements ManagerRefreshTokenRepositoryCustom {

    private final EntityManager em;
}
