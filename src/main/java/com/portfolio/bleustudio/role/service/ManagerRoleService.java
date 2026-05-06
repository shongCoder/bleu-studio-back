package com.portfolio.bleustudio.role.service;

import com.portfolio.bleustudio.common.exception.ErrorEnum;
import com.portfolio.bleustudio.common.exception.RestApiException;
import com.portfolio.bleustudio.manager.entity.Manager;
import com.portfolio.bleustudio.role.entity.ManagerRole;
import com.portfolio.bleustudio.role.repository.ManagerRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@RequiredArgsConstructor
@Validated
public class ManagerRoleService {
    private final ManagerRoleRepository managerRoleRepository;

    @Transactional(readOnly = true)
    public String getManagerRoleName(Manager manager) {
        ManagerRole managerRole = managerRoleRepository.findByManager(manager).orElseThrow(
                () -> new RestApiException(ErrorEnum.MANAGER_ROLE_NOT_FOUND)
        );
        return managerRole.getRole().getRoleName();
    }
}
