package com.portfolio.bleustudio.auth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    private SecretKey signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(Long userId, String role) {
        return createToken(userId, role, null, accessTokenExpiration, TokenType.ACCESS);
    }

    public String createAccessToken(Long userId, String role, String loginId) {
        return createToken(userId, role, loginId, accessTokenExpiration, TokenType.ACCESS);
    }

    public String createRefreshToken(Long userId, String role) {
        return createToken(userId, role, null, refreshTokenExpiration, TokenType.REFRESH);
    }

    public String createRefreshToken(Long userId, String role, String loginId) {
        return createToken(userId, role, loginId, refreshTokenExpiration, TokenType.REFRESH);
    }

    public Long getUserId(String token) {
        return parseClaims(token).get("userId", Long.class);
    }

    public String getRole(String token) {
        return parseClaims(token).get("role", String.class);
    }

    public String getLoginId(String token) {
        return parseClaims(token).get("loginId", String.class);
    }

    public Date getExpiration(String token) {
        return parseClaims(token).getExpiration();
    }

    public Date getAccessTokenExpiryDate() {
        return new Date(System.currentTimeMillis() + accessTokenExpiration);
    }

    public Date getRefreshTokenExpiryDate() {
        return new Date(System.currentTimeMillis() + refreshTokenExpiration);
    }

    public boolean isExpired(String token) {
        return getExpiration(token).before(new Date());
    }

    public boolean validateToken(String token) {
        try {
            return !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private String createToken(Long userId, String role, String loginId, long expiration, TokenType tokenType) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claim("userId", userId)
                .claim("role", role)
                .claim("loginId", loginId)
                .claim("tokenType", tokenType)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(signingKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public TokenType getTokenType(String token) {
        String tokenType = parseClaims(token).get("tokenType", String.class);
        return TokenType.valueOf(tokenType);
    }
}
