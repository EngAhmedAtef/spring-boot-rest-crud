package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.CourseDTO;
import com.ahmedatef.springboot.restcrud.dto.CourseResponse;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.exception.CourseNotFoundException;
import com.ahmedatef.springboot.restcrud.mapper.CourseMapper;
import com.ahmedatef.springboot.restcrud.repository.CourseRepository;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private CourseDTO courseDTO;
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

    @Test
    void CourseService_FindAll_ReturnsEmptyList() {

        mockStatic(CourseMapper.class);

        List<CourseEntity> courses = new ArrayList<>();

        doReturn(courses).when(courseRepository).findAll();
        when(CourseMapper.mapToResponse(courseEntity)).thenReturn(courseResponse);

        assertEquals(0, courseService.findAll().size());

    }

    @Test
    void CourseService_FindById_ReturnsCorrectObject() {

        // Arrange
        // Mock the static mapping method
        mockStatic(CourseMapper.class);

        // Create the required objects
        CourseResponse response = new CourseResponse();
        CourseDTO dto = new CourseDTO();

        // Set objects' variables
        UUID id = UUID.randomUUID();
        dto.setId(id);
        response.setCourse(dto);

        // Stub the necessary methods
        doReturn(Optional.of(courseEntity)).when(courseRepository).findById(any());
        when(CourseMapper.mapToResponse(any())).thenReturn(response);

        // Act
        CourseResponse result = courseService.findById(id);

        // Assert
        assertEquals(id, result.getCourse().getId());
    }

    @Test
    void CourseService_FindById_ThrowsCourseNotFoundException() {

        // Arrange
        // Stub the necessary methods
        doReturn(Optional.empty()).when(courseRepository).findById(any());

        // Act
        // Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.findById(UUID.randomUUID()));
    }

    @Test
    void CourseService_DeleteById_DeletesEntity() {

    }

}