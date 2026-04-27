package com.portfolio.bleustudio.customer.repository;

import com.portfolio.bleustudio.customer.entity.CustomerRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRefreshTokenRepository extends JpaRepository<CustomerRefreshToken, Long>, CustomerRefreshTokenRepositoryCustom {
}
