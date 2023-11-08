package com.ahmedatef.springboot.restcrud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDTO implements Serializable {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String title;
}
