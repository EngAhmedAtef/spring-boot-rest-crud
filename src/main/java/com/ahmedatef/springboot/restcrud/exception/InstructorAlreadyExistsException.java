package com.ahmedatef.springboot.restcrud.exception;

public class InstructorAlreadyExistsException extends RuntimeException {
    public InstructorAlreadyExistsException(String message) {
        super(message);
    }
}