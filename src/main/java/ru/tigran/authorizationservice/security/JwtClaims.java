package ru.tigran.authorizationservice.security;

import io.jsonwebtoken.impl.DefaultClaims;

import java.util.List;

public class JwtClaims extends DefaultClaims {

    public JwtClaims setUsername(String username){
        this.setValue("usr", username);
        return this;
    }

    public JwtClaims setRefreshToken(String refreshToken){
        this.setValue("jrt", refreshToken);
        return this;
    }

    public JwtClaims setAuthorities(List<String> authorities){
        this.setValue("ath", authorities);
        return this;
    }
}
