package com.portfolio.bleustudio.customer.entity;

import com.portfolio.bleustudio.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_no")
    private Long customerNo;

    @Column(name = "login_id", length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "customer_name", length = 50, nullable = false)
    private String customerName;

    @Column(name = "business_name", length = 100)
    private String businessName;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}