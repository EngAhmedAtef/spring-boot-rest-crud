package com.ahmedatef.springboot.restcrud.dto;

import com.ahmedatef.springboot.restcrud.enums.CourseLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private UUID id;
    private String name;
    private Timestamp startDate;
    private Timestamp endDate;
    private CourseLevel courseLevel;
    private Boolean isStarted;
}
