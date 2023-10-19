package com.ahmedatef.springboot.restcrud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseAddAndLinkInstructorRequest {
    private CourseDTO course;
    @JsonProperty(value = "instructor_id")
    private int instructorId;
}