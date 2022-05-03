package ru.tigran.authorizationservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
    @Value("${jwt.token.lifetime}")
    private Integer lifeTime;

    public Integer getLifeTime() {
        return lifeTime;
    }
}
