package com.ahmedatef.springboot.restcrud.controlleradvice;

import com.ahmedatef.springboot.restcrud.error.NotFoundError;
import com.ahmedatef.springboot.restcrud.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundError> handleNotFoundException(NotFoundException e) {
        NotFoundError error = new NotFoundError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}