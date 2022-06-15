package ru.tigran.authorizationservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtToken {
    private final String value;

    private JwtToken(String value) {
        this.value = value;
    }

    public static JwtToken of(Claims claims, JwtProperties properties) {
        long now = System.currentTimeMillis();
        long exp = now + properties.getLifeTime() * 60 * 1000;
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date((now)))
                .setExpiration(new Date(exp))
                .signWith(SignatureAlgorithm.HS512, properties.getSecret())
                .compact();
        return new JwtToken(token);
    }

    public String get() {
        return value;
    }
}
