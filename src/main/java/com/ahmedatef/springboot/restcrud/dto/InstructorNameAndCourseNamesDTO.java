package com.ahmedatef.springboot.restcrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorNameAndCourseNamesDTO {
    private String name;
    private List<String> courseNames;

    public InstructorNameAndCourseNamesDTO(String name, String courseNames) {
        this.name = name;
        this.courseNames = Arrays.stream(courseNames.split(",")).toList();
    }
}
