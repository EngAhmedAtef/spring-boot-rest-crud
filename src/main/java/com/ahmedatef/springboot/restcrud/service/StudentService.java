package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.StudentDTO;
import com.ahmedatef.springboot.restcrud.dto.StudentResponse;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.mapper.StudentMapper;
import com.ahmedatef.springboot.restcrud.repository.StudentRepository;
import com.ahmedatef.springboot.restcrud.entity.StudentEntity;
import com.ahmedatef.springboot.restcrud.exception.StudentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentService {

    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<StudentResponse> findAll() {
        List<StudentEntity> studentEntities = repository.findAll();
        return studentEntities.stream().map(StudentMapper::mapToResponse).toList();
    }

    public StudentResponse findById(UUID id) {
        Optional<StudentEntity> optional = repository.findById(id);
        if (optional.isPresent())
            return StudentMapper.mapToResponse(optional.get());
        else
            throw new StudentNotFoundException("Student id not found - " + id);
    }

    public StudentResponse save(StudentDTO studentDTO) {
        StudentEntity savedEntity = repository.save(MapperUtil.map(studentDTO, StudentEntity.class));
        return StudentMapper.mapToResponse(savedEntity);
    }

    public StudentResponse update(StudentDTO studentDTO) {
        Optional<StudentEntity> optional = repository.findById(studentDTO.getId());
        if (optional.isPresent()) {
            StudentEntity studentEntity = optional.get();
            studentEntity.setId(studentDTO.getId());
            studentEntity.setFirstName(studentDTO.getFirstName());
            studentEntity.setLastName(studentDTO.getLastName());
            studentEntity.setAge(studentDTO.getAge());
            studentEntity.setEmail(studentDTO.getEmail());
            studentEntity.setGender(studentDTO.getGender());
            studentEntity.setPhoneNumber(studentDTO.getPhoneNumber());
            studentEntity.setNationalId(studentDTO.getNationalId());
            StudentEntity savedEntity = repository.save(studentEntity);
            return StudentMapper.mapToResponse(savedEntity);
        } else
            throw new StudentNotFoundException("Student id not found - " + studentDTO.getId());
    }

    public void deleteById(UUID id) {
        Optional<StudentEntity> optional = repository.findById(id);
        if (optional.isPresent())
            repository.delete(optional.get());
        else
            throw new StudentNotFoundException("Student id not found - " + id);
    }
}
