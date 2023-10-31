package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.CourseDTO;
import com.ahmedatef.springboot.restcrud.dto.CourseResponse;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private InstructorEntity instructorEntity;
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

        // Arrange
        // Create a new course entity and set its id to a specific value
        // Create a list to act as a database
        // Add the entity to the database
        UUID id = UUID.randomUUID();
        CourseEntity entity = new CourseEntity();
        entity.setId(id);

        List<CourseEntity> courses = new ArrayList<>() {{
            add(entity);
        }};

        // Stub the necessary methods
        doReturn(Optional.of(entity)).when(courseRepository).findById(id);
        doAnswer(answer -> {
            courses.remove(answer.getArgument(0, CourseEntity.class));
            return null;
        }).when(courseRepository).delete(any(CourseEntity.class));

        // Act
        // Assert
        assertAll(() -> {
            assertEquals(1, courses.size());
            courseService.deleteById(id);
            assertEquals(0, courses.size());
        });

    }

    @Test
    void CourseService_DeleteById_ThrowsCourseNotFoundException() {

        // Arrange
        // Stub the necessary methods
        doReturn(Optional.empty()).when(courseRepository).findById(any());

        // Act
        // Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.deleteById(UUID.randomUUID()));

    }

    @Test
    void CourseService_SaveCourseAndLinkInstructor_SavesObjectsCorrectly() {

        // Arrange
        // Sub the necessary methods


        // Act

        // Assert

    }

}