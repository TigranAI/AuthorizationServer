package ru.tigran.authorizationservice.exception;

public class MessageException extends Exception {
    private String message;
    public MessageException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}