package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.*;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.exception.InstructorAlreadyExistsException;
import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.InvalidEmailAddressException;
import com.ahmedatef.springboot.restcrud.mapper.InstructorMapper;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InstructorService {

    private final InstructorRepository repository;
    private final InstructorValidation instructorValidation;

    @Autowired
    public InstructorService(InstructorRepository repository, InstructorValidation instructorValidation) {
        this.repository = repository;
        this.instructorValidation = instructorValidation;
    }

    public List<InstructorResponse> findAll() {
        List<InstructorEntity> instructors = repository.findAll();
        return instructors.stream()
                .map(InstructorMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public InstructorResponse findById(int id) {
        Optional<InstructorEntity> optional = repository.findById(id);
        if (optional.isPresent())
            return InstructorMapper.mapToResponse(optional.get());
        else
            throw new InstructorNotFoundException("Instructor id not found - " + id);
    }

    public InstructorResponse save(InstructorDTO instructor) {
        if (!validatePhoneNumber(instructor.getPhoneNumber()))
            throw new InstructorAlreadyExistsException("Instructor with phone number " + instructor.getPhoneNumber() + " already exists.");
        if (!validateEmailAddress(instructor.getEmail()))
            throw new InvalidEmailAddressException("Email address " + instructor.getEmail() + " is invalid.");

        InstructorEntity entity = MapperUtil.map(instructor, InstructorEntity.class);
        entity.setId(0);
        InstructorEntity savedEntity = repository.save(entity);
        return InstructorMapper.mapToResponse(savedEntity);
    }

    public InstructorResponse update(InstructorDTO instructor) {
        if (!validatePhoneNumber(instructor.getPhoneNumber()))
            throw new InstructorAlreadyExistsException("Instructor with phone number " + instructor.getPhoneNumber() + " already exists.");
        if (!validateEmailAddress(instructor.getEmail()))
            throw new InvalidEmailAddressException("Email address " + instructor.getEmail() + " is invalid.");

        Optional<InstructorEntity> optional = repository.findById(instructor.getId());
        if (optional.isPresent()) {
            InstructorEntity instructorEntity = optional.get();
            instructorEntity.setId(instructor.getId());
            instructorEntity.setFirstName(instructor.getFirstName());
            instructorEntity.setLastName(instructor.getLastName());
            instructorEntity.setEmail(instructor.getEmail());
            instructorEntity.setTitle(instructor.getTitle());
            instructorEntity.setPhoneNumber(instructor.getPhoneNumber());
            InstructorEntity savedEntity = repository.save(instructorEntity);
            return InstructorMapper.mapToResponse(savedEntity);
        } else
            throw new InstructorNotFoundException("Instructor id not found - " + instructor.getId());
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
        return repository.getInstructorCoursesNames();
    }

    public List<InstructorAndEnrolledStudentsDTO> getInstructorAndEnrolledStudents() {
        List<InstructorEntity> instructorEntities = repository.findAll();
        return instructorEntities.stream()
                .map(InstructorMapper::mapToInstructorAndEnrolledStudents).toList();
    }

    protected boolean validatePhoneNumber(String phoneNumber) {
        System.out.println("Called ValidatePhoneNumber");
        return instructorValidation.validatePhoneNumber(phoneNumber);
    }

    protected boolean validateEmailAddress(String emailAddress) {
        return instructorValidation.validateEmailAddress(emailAddress);
    }

    protected boolean validateYoutubeChannel(String youtubeChannel) {
        return instructorValidation.validateYoutubeChannel(youtubeChannel);
    }
}