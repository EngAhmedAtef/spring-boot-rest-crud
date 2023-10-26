package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.CourseResponse;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.mapper.CourseMapper;
import com.ahmedatef.springboot.restcrud.repository.CourseRepository;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @Test
    void CourseService_FindAll_ReturnsAllValues() {
        CourseRepository courseRepository = mock(CourseRepository.class);
        InstructorRepository instructorRepository = mock(InstructorRepository.class);
        CourseResponse courseResponse = mock(CourseResponse.class);
        CourseEntity courseEntity = mock(CourseEntity.class);

        mockStatic(CourseMapper.class);

        List<CourseEntity> courses = Arrays.asList(courseEntity, courseEntity, courseEntity, courseEntity);

        when(courseRepository.findAll()).thenReturn(courses);
        when(CourseMapper.mapToResponse(courseEntity)).thenReturn(courseResponse);

        CourseService courseService = new CourseService(courseRepository, instructorRepository);

        assertEquals(4, courseService.findAll().size());
    }

}