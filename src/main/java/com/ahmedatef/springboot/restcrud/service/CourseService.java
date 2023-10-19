package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.*;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.enums.CourseLevel;
import com.ahmedatef.springboot.restcrud.exception.CourseNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.UnknownGenderException;
import com.ahmedatef.springboot.restcrud.mapper.CourseMapper;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.repository.CourseRepository;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository repository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public CourseService(CourseRepository repository, InstructorRepository instructorRepository) {
        this.repository = repository;
        this.instructorRepository = instructorRepository;
    }

    public List<CourseResponse> findAll() {
        List<CourseEntity> courseEntities = repository.findAll();
        return courseEntities.stream()
                .map(CourseMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse findById(UUID id) {
        Optional<CourseEntity> optional = repository.findById(id);
        if (optional.isPresent())
            return CourseMapper.mapToResponse(optional.get());
        else
            throw new CourseNotFoundException("Course id not found - " + id);
    }

    public void deleteById(UUID id) {
        Optional<CourseEntity> optional = repository.findById(id);
        if (optional.isPresent()) {
            CourseEntity courseEntity = optional.get();
            repository.delete(courseEntity);
        }
        else
            throw new CourseNotFoundException("Course id not found - " + id);
    }

    public CourseResponse saveCourseAndLinkInstructor(CourseAddAndLinkInstructorRequest addRequest) {
        CourseDTO courseDTO = addRequest.getCourse();
        int instructorId = addRequest.getInstructorId();
        Optional<InstructorEntity> optional = instructorRepository.findById(instructorId);
        if (optional.isPresent()) {
            CourseEntity courseEntity = MapperUtil.map(courseDTO, CourseEntity.class);
            courseEntity.setInstructor(optional.get());
            CourseEntity savedEntity = repository.save(courseEntity);
            return CourseMapper.mapToResponse(savedEntity);
        } else
            throw new InstructorNotFoundException("Instructor id not found - " + instructorId);
    }

    public CourseResponse update(CourseDTO courseDTO) {
        Optional<CourseEntity> optional = repository.findById(courseDTO.getId());
        if (optional.isPresent()) {
            CourseEntity courseEntity = optional.get();
            courseEntity.setName(courseDTO.getName());
            courseEntity.setStartDate(courseDTO.getStartDate());
            courseEntity.setEndDate(courseDTO.getEndDate());
            courseEntity.setIsStarted(courseDTO.getIsStarted());
            courseEntity.setCourseLevel(courseDTO.getCourseLevel());
            CourseEntity savedEntity = repository.save(courseEntity);
            return CourseMapper.mapToResponse(savedEntity);
        } else
            throw new CourseNotFoundException("Course id not found - " + courseDTO.getId());
    }

    public List<CourseNameStartDateEnrolledStudentsDTO> getNameStartDateEnrolledStudents() {
        List<CourseEntity> courseEntities = repository.findAll();
        return courseEntities.stream().map(CourseMapper::mapToNameStartDateEnrolledStudents).toList();
    }

    public List<CourseLevelEnrolledStudents> getCourseLevelEnrolledStudents(String level) {
        CourseLevel inputLevel;
        try {
            inputLevel = CourseLevel.valueOf(level);
        } catch (IllegalArgumentException e) {
            throw new UnknownGenderException("level values can be one of the following: " + Arrays.stream(CourseLevel.values()).map(String::valueOf).toList());
        }

        List<CourseEntity> courseEntities = repository.findAll();
        return courseEntities.stream()
                .filter(courseEntity -> courseEntity.getCourseLevel() == inputLevel)
                .map(courseEntity -> {
                    List<String> studentNames = courseEntity.getStudents().stream().map(student -> student.getFirstName() + " " + student.getLastName()).toList();
                    return new CourseLevelEnrolledStudents(
                            courseEntity.getName(),
                            courseEntity.getCourseLevel(),
                            studentNames
                    );
                }).toList();
    }
}
