package ru.tigran.authorizationservice.exception;

public class JwtTokenMalformedException extends MessageException {
    public JwtTokenMalformedException(String message) {
        super(message);
    }
}