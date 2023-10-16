package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dao.CourseRepository;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.exception.CourseNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService implements EntityService<CourseEntity> {

    private final CourseRepository repository;

    @Autowired
    public CourseService(CourseRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CourseEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public <D> CourseEntity findById(D id) {
        Optional<CourseEntity> course = repository.findById((UUID) id);
        if (course.isPresent())
            return course.get();
        else
            throw new CourseNotFoundException("Course id not found - " + id);
    }

    @Override
    public CourseEntity save(CourseEntity entity) {
        return repository.save(entity);
    }

    @Override
    public <D> void deleteById(D id) {
        repository.deleteById((UUID) id);
    }
}
