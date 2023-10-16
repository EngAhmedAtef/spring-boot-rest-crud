package com.ahmedatef.springboot.restcrud.exception;

public class InstructorNotFoundException extends NotFoundException {
    public InstructorNotFoundException(String message) {
        super(message);
    }
}