package com.ahmedatef.springboot.restcrud.exception;

public class InstructorDetailsNotFoundException extends NotFoundException {
    public InstructorDetailsNotFoundException(String message) {
        super(message);
    }
}
