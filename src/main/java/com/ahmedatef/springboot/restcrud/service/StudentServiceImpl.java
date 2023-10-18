//package com.ahmedatef.springboot.restcrud.service;
//
//import com.ahmedatef.springboot.restcrud.repository.StudentRepository;
//import com.ahmedatef.springboot.restcrud.entity.StudentEntity;
//import com.ahmedatef.springboot.restcrud.exception.StudentNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class StudentServiceImpl implements StudentService {
//
//    private final StudentRepository repository;
//
//    public StudentServiceImpl(StudentRepository repository) {
//        this.repository = repository;
//    }
//
//    @Override
//    public List<StudentEntity> findAll() {
//        return repository.findAll();
//    }
//
//    @Override
//    public <D> StudentEntity findById(D id) {
//        Optional<StudentEntity> optional = repository.findById((UUID) id);
//        if (optional.isPresent())
//            return optional.get();
//        else
//            throw new StudentNotFoundException("Student id not found - " + id);
//    }
//
//    @Override
//    public StudentEntity save(StudentEntity entity) {
//        return repository.save(entity);
//    }
//
//    @Override
//    public <D> void deleteById(D id) {
//        repository.deleteById((UUID) id);
//    }
//}
