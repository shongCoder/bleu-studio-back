package com.portfolio.bleustudio.manager.repository;

import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.manager.vo.ManagerLoginIdVO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long>, ManagerRepositoryCustom {
    Optional<Manager> findByLoginId(String loginId);

    Optional<Manager> findByEmail(String value);
}
