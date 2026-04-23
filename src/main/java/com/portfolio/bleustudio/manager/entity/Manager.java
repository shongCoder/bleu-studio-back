package com.portfolio.bleustudio.manager.entity;


import com.portfolio.bleustudio.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "manager",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_manager_login_id", columnNames = "login_id"),
                @UniqueConstraint(name = "uk_manager_email", columnNames = "email")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Manager extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_no")
    private Long managerNo;

    @Column(name = "login_id", length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "use_state", nullable = false)
    @Builder.Default
    private Boolean useState = true;

    public void changePassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void updateProfile(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public void markLoginSuccess(LocalDateTime loginAt) {
        this.lastLoginAt = loginAt;
    }

    public void deactivate() {
        this.useState = false;
    }

    public void activate() {
        this.useState = true;
    }
}
