package com.ahmedatef.springboot.restcrud.service;

import java.util.List;

public interface EntityService<T> {
    List<T> findAll();
    <D> T findById(D id);
    T save(T entity);
    <D> void deleteById(D id);
}