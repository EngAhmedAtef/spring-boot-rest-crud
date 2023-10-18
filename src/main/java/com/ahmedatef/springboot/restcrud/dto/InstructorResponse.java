package com.ahmedatef.springboot.restcrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorResponse {
    private InstructorDTO instructor;
    private InstructorDetailsDTO instructorDetails;
    private List<String> courseNames;
}