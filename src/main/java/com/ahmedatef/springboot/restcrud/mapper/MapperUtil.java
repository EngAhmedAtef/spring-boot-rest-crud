package com.ahmedatef.springboot.restcrud.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public final class MapperUtil {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    public static <T, D> D map(T object, Class<D> targetClass) {
        String json = objectMapper.writeValueAsString(object);
        return objectMapper.readValue(json, targetClass);
    }

}