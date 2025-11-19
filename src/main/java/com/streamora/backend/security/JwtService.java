package com.streamora.backend.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24 hours
    private static final String SECRET_KEY = "THIS_IS_A_SECRET_KEY_CHANGE_IT_123456789_ABCDEF";

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ======================================
    // GENERAR TOKEN
    // ======================================
    public String generateToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ======================================
    // EXTRAER EMAIL DEL TOKEN
    // ======================================
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ======================================
    // VALIDAR TOKEN (USADO POR JwtAuthFilter)
    // ======================================
    public boolean isTokenValid(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {

        final String email = extractEmail(token);

        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // ======================================
    // VER SI EL TOKEN ESTÁ EXPIRADO
    // ======================================
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAllClaims(token).getExpiration();
    }

    // ======================================
    // OBTENER CLAIMS
    // ======================================
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

