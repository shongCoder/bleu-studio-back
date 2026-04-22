package com.portfolio.bleustudio.manager.repository;

import com.portfolio.bleustudio.manager.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long>, ManagerRepositoryCustom {
}
