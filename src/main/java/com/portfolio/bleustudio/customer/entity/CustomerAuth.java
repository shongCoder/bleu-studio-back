package com.portfolio.bleustudio.customer.entity;

import com.portfolio.bleustudio.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "customer_auth",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_provider_user",
                        columnNames = {"provider", "provider_user_id"}
                )
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CustomerAuth extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auth_no")
    private Long authNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_no", nullable = false)
    private Customer customer;

    /** 로그인 구분 */
    @Column(name = "provider", length = 20, nullable = false)
    private String provider;

    /** 제3자 계정 id */
    @Column(name = "provider_user_id", length = 100, nullable = false)
    private String providerUserId;

    @Column(name = "refresh_token", length = 512)
    private String refreshToken;
}
