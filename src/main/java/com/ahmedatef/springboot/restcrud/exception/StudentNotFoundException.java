package com.ahmedatef.springboot.restcrud.exception;

public class StudentNotFoundException extends NotFoundException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}