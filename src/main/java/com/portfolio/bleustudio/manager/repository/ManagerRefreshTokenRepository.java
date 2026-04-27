package com.portfolio.bleustudio.manager.repository;

import com.portfolio.bleustudio.manager.entity.ManagerRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRefreshTokenRepository extends JpaRepository<ManagerRefreshToken, Long>, ManagerRefreshTokenRepositoryCustom {
}
