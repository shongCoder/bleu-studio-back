package com.portfolio.bleustudio.auth.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthenticatedManager {

    private final Long managerNo;

    private final String loginId;

    private final String role;
}
