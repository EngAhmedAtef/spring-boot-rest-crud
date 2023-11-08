package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.cache.RedisService;
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
    private final RedisService redisService;

    private static final String REDIS_INSTRUCTORS_QUEUE = "instructors";

    @Autowired
    public InstructorService(InstructorRepository repository, InstructorValidation instructorValidation, RedisService redisService) {
        this.repository = repository;
        this.instructorValidation = instructorValidation;
        this.redisService = redisService;
    }

    public List<InstructorResponse> findAll() {
        if (redisService.hasKey(REDIS_INSTRUCTORS_QUEUE)) {
            return redisService.popList(REDIS_INSTRUCTORS_QUEUE).get().stream().map(object -> (InstructorResponse) object).collect(Collectors.toList());
        } else {
            List<InstructorEntity> instructors = repository.findAll();
            return instructors.stream()
                    .map(InstructorMapper::mapToResponse)
                    .peek(response -> redisService.pushToList(REDIS_INSTRUCTORS_QUEUE, response))
                    .collect(Collectors.toList());
        }
    }

    public InstructorResponse findById(int id) {
        if (redisService.hasKey(String.valueOf(id)))
            return (InstructorResponse) redisService.getData(String.valueOf(id)).get();
        else {
            Optional<InstructorEntity> optional = repository.findById(id);
            if (optional.isPresent()) {
                InstructorResponse response = InstructorMapper.mapToResponse(optional.get());
                redisService.cacheData(String.valueOf(id), response);
                return response;
            } else
                throw new InstructorNotFoundException("Instructor id not found - " + id);
        }

    }

    public InstructorResponse save(InstructorDTO instructor) {
//        if (validatePhoneNumber(instructor.getPhoneNumber()))
//            throw new InstructorAlreadyExistsException("Instructor with phone number " + instructor.getPhoneNumber() + " already exists.");
//        if (!validateEmailAddress(instructor.getEmail()))
//            throw new InvalidEmailAddressException("Email address " + instructor.getEmail() + " is invalid.");

        InstructorEntity entity = MapperUtil.map(instructor, InstructorEntity.class);
        entity.setId(0);
        InstructorEntity savedEntity = repository.save(entity);
        InstructorResponse response = InstructorMapper.mapToResponse(savedEntity);
        redisService.cacheData(String.valueOf(instructor.getId()), response);
        if (redisService.hasKey(REDIS_INSTRUCTORS_QUEUE))
            redisService.pushToList(REDIS_INSTRUCTORS_QUEUE, response);
        return response;
    }

    public InstructorResponse update(InstructorDTO instructor) {
//        if (validatePhoneNumber(instructor.getPhoneNumber()))
//            throw new InstructorAlreadyExistsException("Instructor with phone number " + instructor.getPhoneNumber() + " already exists.");
//        if (!validateEmailAddress(instructor.getEmail()))
//            throw new InvalidEmailAddressException("Email address " + instructor.getEmail() + " is invalid.");

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
            InstructorResponse response = InstructorMapper.mapToResponse(savedEntity);
            redisService.cacheData(String.valueOf(instructor.getId()), response);
            redisService.deleteKey(REDIS_INSTRUCTORS_QUEUE);
            return response;
        } else
            throw new InstructorNotFoundException("Instructor id not found - " + instructor.getId());
    }

    public void deleteById(int id) {
        Optional<InstructorEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            InstructorEntity instructor = optional.get();
            repository.delete(instructor);
            redisService.deleteKey(String.valueOf(id));
            redisService.deleteKey(REDIS_INSTRUCTORS_QUEUE);
        } else
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