package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.*;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
import com.ahmedatef.springboot.restcrud.mapper.InstructorMapper;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final InstructorRepository repository;

    @Autowired
    public InstructorService(InstructorRepository repository) {
        this.repository = repository;
    }

    public List<InstructorResponse> findAll() {
        List<InstructorEntity> instructors = repository.findAll();
        return instructors.stream()
                .map(InstructorMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public <D> InstructorResponse findById(D id) {
        Optional<InstructorEntity> optional = repository.findById((Integer) id);
        if (optional.isPresent())
            return InstructorMapper.mapToResponse(optional.get());
        else
            throw new InstructorNotFoundException("Instructor id not found - " + id);
    }

    public InstructorResponse save(InstructorDTO instructor) {
        InstructorEntity entity = MapperUtil.map(instructor, InstructorEntity.class);
        InstructorEntity savedEntity = repository.save(entity);
        return InstructorMapper.mapToResponse(savedEntity);
    }

    public void deleteById(int id) {
        Optional<InstructorEntity> optional = repository.findById(id);
        if(optional.isPresent()) {
            InstructorEntity instructor = optional.get();
            repository.delete(instructor);
        }
        else
            throw new InstructorNotFoundException("Instructor id not found - " + id);
    }

    public List<InstructorNameAndCourseNamesDTO> getInstructorAndCourses() {
        List<InstructorEntity> instructorEntities = repository.findAll();
        return instructorEntities.stream()
                .map(InstructorMapper::mapToNameAndCourseNames)
                .toList();
    }

    public List<InstructorAndEnrolledStudentsDTO> getInstructorAndEnrolledStudents() {
        List<InstructorEntity> instructorEntities = repository.findAll();
        return instructorEntities.stream()
                .map(InstructorMapper::mapToInstructorAndEnrolledStudents).toList();
    }
}