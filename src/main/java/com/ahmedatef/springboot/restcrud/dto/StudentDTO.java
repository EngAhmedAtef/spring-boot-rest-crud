package com.ahmedatef.springboot.restcrud.dto;

import com.ahmedatef.springboot.restcrud.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String email;
    private String phoneNumber;
    private String nationalId;
}