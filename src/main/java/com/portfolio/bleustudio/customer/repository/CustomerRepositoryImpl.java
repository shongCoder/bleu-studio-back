package com.portfolio.bleustudio.customer.repository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepositoryCustom{

    private final EntityManager em;
}
