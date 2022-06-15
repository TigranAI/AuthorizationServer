package ru.tigran.authorizationservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
    @Value("${jwt.token.lifetime}")
    private Integer lifeTime;
    @Value("${jwt.token.secret}")
    private String secret;

    public Integer getLifeTime() {
        return lifeTime;
    }

    public String getSecret() {
        return secret;
    }
}
