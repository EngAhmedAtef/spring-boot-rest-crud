package com.ahmedatef.springboot.restcrud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InstructorDetailsDTO implements Serializable {
    private UUID id;
    private String youtubeChannel;
    private String[] hobbies;
}