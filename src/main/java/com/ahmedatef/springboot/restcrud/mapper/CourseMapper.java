package com.ahmedatef.springboot.restcrud.mapper;

import com.ahmedatef.springboot.restcrud.dto.CourseDTO;
import com.ahmedatef.springboot.restcrud.dto.CourseNameStartDateEnrolledStudentsDTO;
import com.ahmedatef.springboot.restcrud.dto.CourseResponse;
import com.ahmedatef.springboot.restcrud.dto.InstructorDTO;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;

import java.sql.Timestamp;
import java.util.List;

public final class CourseMapper {

    public static CourseResponse mapToResponse(CourseEntity courseEntity) {
        CourseDTO courseDTO = new CourseDTO(
                courseEntity.getId(),
                courseEntity.getName(),
                courseEntity.getStartDate(),
                courseEntity.getEndDate(),
                courseEntity.getCourseLevel(),
                courseEntity.getIsStarted()
        );
        InstructorDTO instructorDTO = new InstructorDTO(
                courseEntity.getInstructor().getId(),
                courseEntity.getInstructor().getFirstName(),
                courseEntity.getInstructor().getLastName(),
                courseEntity.getInstructor().getEmail(),
                courseEntity.getInstructor().getPhoneNumber(),
                courseEntity.getInstructor().getTitle()
        );

        return new CourseResponse(courseDTO, instructorDTO);
    }

    public static CourseNameStartDateEnrolledStudentsDTO mapToNameStartDateEnrolledStudents(CourseEntity courseEntity) {
        String name = courseEntity.getName();
        Timestamp startDate = courseEntity.getStartDate();
        List<String> studentNames = courseEntity.getStudents().stream().map(student -> student.getFirstName() + " " + student.getLastName()).toList();
        return new CourseNameStartDateEnrolledStudentsDTO(name, startDate, studentNames);
    }

}