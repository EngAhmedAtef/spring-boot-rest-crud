package com.ahmedatef.springboot.restcrud.exception;

public class UnknownGenderException extends RuntimeException {
    public UnknownGenderException(String message) {
        super(message);
    }
}