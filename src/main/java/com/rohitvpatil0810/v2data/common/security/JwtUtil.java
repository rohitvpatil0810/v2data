package com.rohitvpatil0810.v2data.common.security;

import com.rohitvpatil0810.v2data.common.api.exceptions.BadTokenException;
import com.rohitvpatil0810.v2data.common.api.exceptions.TokenExpiredException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
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
    //    private final long refreshTokenValidity = 7 * 24 * 60 * 60; // 7 days
    private final long refreshTokenValidity = 30; // 7 days

    @Value("${jwt.secret.access}")
    private String accessTokenSecret;
    @Value("${jwt.secret.refresh}")
    private String refreshTokenSecret;

    private Claims validateToken(String token, String secretKey) {
        return Jwts.parser()
                .verifyWith(generateKey(secretKey))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims verifyRefreshToken(String refreshToken) throws TokenExpiredException, BadTokenException {
        try {
            return validateToken(refreshToken, refreshTokenSecret);
        } catch (ExpiredJwtException ex) {
            throw new TokenExpiredException("Refresh token expired.");
        } catch (JwtException ex) {
            throw new BadTokenException("Invalid refresh token");
        }
    }

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
