package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.CourseResponse;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.mapper.CourseMapper;
import com.ahmedatef.springboot.restcrud.repository.CourseRepository;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private CourseResponse courseResponse;
    @Mock
    private CourseEntity courseEntity;
    @InjectMocks
    CourseService courseService;

    @Test
    void CourseService_FindAll_ReturnsAllValues() {

        mockStatic(CourseMapper.class);

        List<CourseEntity> courses = Arrays.asList(courseEntity, courseEntity, courseEntity, courseEntity);

        doReturn(courses).when(courseRepository).findAll();
        when(CourseMapper.mapToResponse(courseEntity)).thenReturn(courseResponse);

        assertEquals(4, courseService.findAll().size());

    }

}