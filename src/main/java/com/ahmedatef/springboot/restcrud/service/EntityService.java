package com.ahmedatef.springboot.restcrud.service;

import java.util.List;

public interface EntityService<T> {
    List<T> findAll();
    T findById(int id);
    T save(T entity);
    void deleteById(int id);
}