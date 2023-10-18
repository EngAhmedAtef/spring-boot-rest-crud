package com.ahmedatef.springboot.restcrud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDetailsDTO {
    private UUID id;
    private String youtubeChannel;
    private String[] hobbies;
}