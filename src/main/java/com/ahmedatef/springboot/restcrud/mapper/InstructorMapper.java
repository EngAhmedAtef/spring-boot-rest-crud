package com.ahmedatef.springboot.restcrud.mapper;

import com.ahmedatef.springboot.restcrud.dto.*;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;

import java.util.ArrayList;
import java.util.List;

public final class InstructorMapper {

    public static InstructorResponse mapToResponse(InstructorEntity instructor) {
        InstructorDTO instructorDTO = new InstructorDTO(
                instructor.getId(),
                instructor.getFirstName(),
                instructor.getLastName(),
                instructor.getEmail(),
                instructor.getPhoneNumber(),
                instructor.getTitle()
        );
        InstructorDetailsDTO instructorDetailsDTO = instructor.getDetails() != null ? new InstructorDetailsDTO(
                instructor.getDetails().getId(),
                instructor.getDetails().getYoutubeChannel(),
                instructor.getDetails().getHobbies()
        ) : null;
        List<String> courseNames;
        if (instructor.getCourses() != null && !instructor.getCourses().isEmpty()) {
            courseNames = new ArrayList<>();
            instructor.getCourses().forEach(courseEntity -> courseNames.add(courseEntity.getName()));
        } else
            courseNames = null;
        return new InstructorResponse(instructorDTO, instructorDetailsDTO, courseNames);
    }

    public static InstructorNameAndCourseNamesDTO mapToNameAndCourseNames(InstructorEntity instructor) {
        String name = instructor.getFirstName() + " " + instructor.getLastName();
        List<String> courseNames = instructor.getCourses().stream()
                .map(CourseEntity::getName)
                .toList();
        return new InstructorNameAndCourseNamesDTO(name, courseNames);
    }

    public static InstructorAndEnrolledStudentsDTO mapToInstructorAndEnrolledStudents(InstructorEntity instructor) {
        String name = instructor.getFirstName() + " " + instructor.getLastName();
        List<CourseAndEnrolledStudentsDTO> courseAndEnrolledStudentsDTOS = instructor.getCourses().stream().map(courseEntity -> {
            String courseName = courseEntity.getName();
            List<String> enrolledStudents = courseEntity.getStudents().stream().map(student -> student.getFirstName() + " " + student.getLastName()).toList();
            return new CourseAndEnrolledStudentsDTO(courseName, enrolledStudents);
        }).toList();
        return new InstructorAndEnrolledStudentsDTO(name, courseAndEnrolledStudentsDTOS);
    }

}