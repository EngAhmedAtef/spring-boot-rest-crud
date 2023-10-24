package com.ahmedatef.springboot.restcrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstructorDetailsResponse {
    private int instructorId;
    private String firstName;
    private String lastName;
    private InstructorDetailsDTO details;
}
