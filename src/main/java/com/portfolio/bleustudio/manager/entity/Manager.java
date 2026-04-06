package com.portfolio.bleustudio.manager;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "manager")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Manager {
}
