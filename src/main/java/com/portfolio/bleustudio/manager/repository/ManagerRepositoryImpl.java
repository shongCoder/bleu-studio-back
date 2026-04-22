package com.portfolio.bleustudio.manager.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ManagerRepositoryImpl implements ManagerRepositoryCustom {

    private final EntityManager em;
}
