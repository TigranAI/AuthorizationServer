package ru.tigran.authorizationservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {
    @Value("${jwt.loginUrl}")
    private String loginUrl;
    @Value("${jwt.token.header}")
    private String header;
    @Value("${jwt.token.prefix}")
    private String prefix;
    @Value("${jwt.token.lifetime}")
    private Integer lifeTime;
    @Value("${jwt.token.secret}")
    private String secret;

    public String getLoginUrl() {
        return loginUrl;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public Integer getLifeTime() {
        return lifeTime;
    }

    public String getSecret() {
        return secret;
    }
}
