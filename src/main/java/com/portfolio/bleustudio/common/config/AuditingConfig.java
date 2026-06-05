package com.portfolio.bleustudio.common.config;

import com.portfolio.bleustudio.auth.security.AuthenticatedManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Configuration
public class AuditingConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            Object principal = authentication.getPrincipal();
            if (principal instanceof AuthenticatedManager manager && StringUtils.hasText(manager.getLoginId())) {
                return Optional.of(manager.getLoginId());
            }

            return Optional.empty();
        };
    }
}
