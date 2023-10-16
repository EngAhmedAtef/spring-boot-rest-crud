package com.ahmedatef.springboot.restcrud.error;

import lombok.Data;

@Data
public class NotFoundError {
    private final int status;
    private final String message;
}
