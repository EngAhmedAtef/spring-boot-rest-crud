package com.ahmedatef.springboot.restcrud.mapper;

import com.ahmedatef.springboot.restcrud.dto.StudentDTO;
import com.ahmedatef.springboot.restcrud.dto.StudentResponse;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.entity.StudentEntity;

import java.util.List;

public final class StudentMapper {

    public static StudentResponse mapToResponse(StudentEntity studentEntity) {
        StudentDTO studentDTO = new StudentDTO(
                studentEntity.getId(),
                studentEntity.getFirstName(),
                studentEntity.getLastName(),
                studentEntity.getAge(),
                studentEntity.getGender(),
                studentEntity.getEmail(),
                studentEntity.getPhoneNumber(),
                studentEntity.getNationalId()
        );
        List<String> courseNames = studentEntity.getCourses().stream()
                .map(CourseEntity::getName)
                .toList();
        return new StudentResponse(studentDTO, courseNames);
    }

}