package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dao.InstructorRepository;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements EntityService<InstructorEntity> {

    private final InstructorRepository repository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InstructorEntity> findAll() {
        return repository.findAll();
    }

    @Override
    public InstructorEntity findById(int id) {
        Optional<InstructorEntity> optional = repository.findById(id);
        if (optional.isPresent())
            return optional.get();
        else
            throw new InstructorNotFoundException("Instructor id not found - " + id);
    }

    @Override
    public InstructorEntity save(InstructorEntity instructor) {
        return repository.save(instructor);
    }

    @Override
    public void deleteById(int id) {
        repository.deleteById(id);
    }
}