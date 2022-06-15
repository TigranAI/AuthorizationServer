package ru.tigran.authorizationservice.controller.dto;

public class RefreshTokenDto {
    private String jrt;

    public String jrt() {
        return jrt;
    }

    public void setJrt(String jrt) {
        this.jrt = jrt;
    }
}
