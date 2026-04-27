package com.portfolio.bleustudio.auth.enums;

import java.util.Arrays;

public enum AuthLevel {
    SYSTEM("SYSTEM", "시스템관리자"),
    ADMIN("ADMIN", "관리자"),
    MANAGER("MANAGER", "매니저");

    private final String roleName;
    private final String description;

    AuthLevel(String roleName, String description) {
        this.roleName = roleName;
        this.description = description;
    }

    public String getRoleName() {
        return roleName;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthority() {
        return "ROLE_" + roleName;
    }

    public static AuthLevel fromRoleName(String roleName) {
        return Arrays.stream(values())
                .filter(level -> level.roleName.equalsIgnoreCase(roleName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown auth level: " + roleName));
    }
}
