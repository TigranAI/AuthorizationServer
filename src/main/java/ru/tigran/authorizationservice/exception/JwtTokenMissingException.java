package ru.tigran.authorizationservice.exception;

public class JwtTokenMissingException extends MessageException {
    public JwtTokenMissingException(String message) {
        super(message);
    }
}