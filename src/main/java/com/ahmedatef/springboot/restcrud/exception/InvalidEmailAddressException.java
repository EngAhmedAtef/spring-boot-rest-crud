package com.ahmedatef.springboot.restcrud.exception;

public class InvalidEmailAddressException extends RuntimeException {
    public InvalidEmailAddressException(String message) {
        super(message);
    }
}