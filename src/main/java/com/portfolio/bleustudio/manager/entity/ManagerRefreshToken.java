package com.portfolio.bleustudio.manager.entity;

import com.portfolio.bleustudio.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "manager_refresh_token",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_manager_refresh_token_token", columnNames = "refresh_token")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ManagerRefreshToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "manager_refresh_token_no")
    private Long managerRefreshTokenNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_no", nullable = false)
    private Manager manager;

    @Column(name = "refresh_token", length = 512, nullable = false, unique = true)
    private String refreshToken;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "revoked", nullable = false)
    @Builder.Default
    private Boolean revoked = false;

    @Column(name = "revoked_at")
    private LocalDateTime revokedAt;

    public void rotate(String refreshToken, LocalDateTime expiresAt) {
        this.refreshToken = refreshToken;
        this.expiresAt = expiresAt;
        this.revoked = false;
        this.revokedAt = null;
    }

    public void revoke(LocalDateTime revokedAt) {
        this.revoked = true;
        this.revokedAt = revokedAt;
    }
}
