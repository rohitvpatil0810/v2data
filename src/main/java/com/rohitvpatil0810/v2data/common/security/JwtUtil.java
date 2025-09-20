package com.rohitvpatil0810.v2data.common.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private final long accessTokenValidity = 15 * 60; // 15 min
    private final long refreshTokenValidity = 7 * 24 * 60 * 60; // 7 days
    @Value("${jwt.secret.access}")
    private String accessTokenSecret;
    @Value("${jwt.secret.refresh}")
    private String refreshTokenSecret;

    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("token_type", "access")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(accessTokenValidity)))
                .signWith(generateKey(accessTokenSecret))
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("token_type", "refresh")
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(refreshTokenValidity)))
                .signWith(generateKey(refreshTokenSecret))
                .compact();
    }

    private SecretKey generateKey(String secret) {
        log.debug("secret Key = {}", secret);
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
