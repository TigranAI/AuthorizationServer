package ru.tigran.authorizationservice.security;

import io.jsonwebtoken.impl.DefaultClaims;

import java.util.List;

public class JwtClaims extends DefaultClaims {

    public String getUsername(){
        return this.getString("usr");
    }

    public JwtClaims setUsername(String username){
        this.setValue("usr", username);
        return this;
    }

    public String getRefreshToken(){
        return this.getString("jrt");
    }

    public JwtClaims setRefreshToken(String refreshToken){
        this.setValue("jrt", refreshToken);
        return this;
    }

    public String getAuthorities(){
        return this.getString("ath");
    }

    public JwtClaims setAuthorities(List<String> authorities){
        this.setValue("ath", authorities);
        return this;
    }
}
