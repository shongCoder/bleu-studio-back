package com.portfolio.bleustudio.customer.entity;

import com.portfolio.bleustudio.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_customer_email", columnNames = "email")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_no")
    private Long customerNo;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "customer_name", length = 50, nullable = false)
    private String customerName;

    @Column(name = "business_name", length = 100)
    private String businessName;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "dormant", nullable = false)
    @Builder.Default
    private boolean dormant = false;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateProfile(String customerName, String businessName, String phone, String email) {
        this.customerName = customerName;
        this.businessName = businessName;
        this.phone = phone;
        this.email = email;
    }

    public void markLoginSuccess(LocalDateTime loginAt) {
        this.lastLoginAt = loginAt;
        this.dormant = false;
    }

    public void changeDormant(boolean dormant) {
        this.dormant = dormant;
    }
}
