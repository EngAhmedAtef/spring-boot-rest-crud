package com.ahmedatef.springboot.restcrud.exception;

public class CourseNotFoundException extends NotFoundException {
    public CourseNotFoundException(String message) {
        super(message);
    }
}
