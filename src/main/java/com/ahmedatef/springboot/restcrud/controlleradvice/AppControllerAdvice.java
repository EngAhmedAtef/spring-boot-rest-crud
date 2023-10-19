package com.ahmedatef.springboot.restcrud.controlleradvice;

import com.ahmedatef.springboot.restcrud.error.CodeMessageError;
import com.ahmedatef.springboot.restcrud.exception.NotFoundException;
import com.ahmedatef.springboot.restcrud.exception.UnknownGenderException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestControllerAdvice
public class AppControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<CodeMessageError> handleNotFoundException(NotFoundException e) {
        CodeMessageError error = new CodeMessageError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnknownGenderException.class)
    public ResponseEntity<CodeMessageError> handleUnknownGenderException(UnknownGenderException e) {
        CodeMessageError error = new CodeMessageError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentTypeMismatchException e) {
        Map<String, String> map = new HashMap<>();
        map.put(e.getPropertyName(), "Required type is " + Objects.requireNonNull(e.getRequiredType()).getSimpleName());
        return map;
    }

}