//package com.ahmedatef.springboot.restcrud.service;
//
//import com.ahmedatef.springboot.restcrud.entity.InstructorDetailsEntity;
//import com.ahmedatef.springboot.restcrud.exception.InstructorDetailsNotFoundException;
//import com.ahmedatef.springboot.restcrud.repository.InstructorDetailsRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Service
//public class InstructorDetailsServiceImpl {
//
//    private final InstructorDetailsRepository repository;
//
//    @Autowired
//    public InstructorDetailsServiceImpl(InstructorDetailsRepository repository) {
//        this.repository = repository;
//    }
//
//    public List<InstructorDetailsEntity> findAll() {
//        return repository.findAll();
//    }
//
//    public <D> InstructorDetailsEntity findById(D id) {
//        Optional<InstructorDetailsEntity> optional = repository.findById((UUID) id);
//        if (optional.isPresent())
//            return optional.get();
//        else
//            throw new InstructorDetailsNotFoundException("Instructor Details id not found - " + id);
//    }
//
//    public InstructorDetailsEntity save(InstructorDetailsEntity entity) {
//        return repository.save(entity);
//    }
//
//    public <D> void deleteById(D id) {
//        repository.deleteById((UUID) id);
//    }
//}
