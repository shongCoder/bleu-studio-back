package com.portfolio.bleustudio.role.entity;

import com.portfolio.bleustudio.common.entity.BaseEntity;
import com.portfolio.bleustudio.manager.entity.Manager;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "manager_role")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ManagerRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_no", nullable = false)
    private Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_no", nullable = false)
    private Role role;
}