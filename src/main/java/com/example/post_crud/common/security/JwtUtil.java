package com.example.post_crud.common.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

@Component
public class JwtUtil {

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String build(UUID userId, long expirationSeconds) {

        long now = Instant.now().getEpochSecond();
        long expiredAt = now + expirationSeconds;

        return Jwts.builder()
                .claim("sub", userId.toString())
                .claim("iat", now)
                .claim("exp", expiredAt)
                .signWith(this.secretKey)
                .compact();
    }

    public Jws<Claims> verify(String token) {
        return Jwts.parser()
                .verifyWith(this.secretKey)
                .build()
                .parseSignedClaims(token);
    }

    public UUID getUserId(String token) {
        Jws<Claims> claims = this.verify(token);
        return UUID.fromString(claims.getPayload().getSubject());
    }

}
