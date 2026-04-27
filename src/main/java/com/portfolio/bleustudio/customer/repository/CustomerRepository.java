package com.portfolio.bleustudio.customer.repository;

import com.portfolio.bleustudio.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>, CustomerRepositoryCustom {
}
