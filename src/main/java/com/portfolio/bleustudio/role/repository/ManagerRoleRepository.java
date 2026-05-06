package com.portfolio.bleustudio.role.repository;

import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.role.entity.ManagerRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRoleRepository extends JpaRepository<ManagerRole, Long> {
    Optional<ManagerRole> findByManager(Manager manager);
}
