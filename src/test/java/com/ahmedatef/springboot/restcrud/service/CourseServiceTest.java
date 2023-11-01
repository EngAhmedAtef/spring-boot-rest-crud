package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.*;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.enums.CourseLevel;
import com.ahmedatef.springboot.restcrud.exception.CourseNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.UnknownGenderException;
import com.ahmedatef.springboot.restcrud.mapper.CourseMapper;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.repository.CourseRepository;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Timestamp;
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
    private CourseService courseService;

    private static MockedStatic<CourseMapper> courseMapperMockedStatic;
    private static MockedStatic<MapperUtil> mapperUtilMockedStatic;

    @BeforeAll
    static void createStaticMocks() {
        courseMapperMockedStatic = mockStatic(CourseMapper.class);
        mapperUtilMockedStatic = mockStatic(MapperUtil.class);
    }

    @AfterAll
    static void destroyStaticMocks() {
        courseMapperMockedStatic.close();
        mapperUtilMockedStatic.close();
    }

    @Test
    void CourseService_FindAll_ReturnsAllValues() {
        // Arrange
        List<CourseEntity> courses = Arrays.asList(courseEntity, courseEntity, courseEntity, courseEntity);

        doReturn(courses).when(courseRepository).findAll();
        when(CourseMapper.mapToResponse(courseEntity)).thenReturn(courseResponse);

        // Act
        // Assert
        assertEquals(4, courseService.findAll().size());

    }

    @Test
    void CourseService_FindAll_ReturnsEmptyList() {
        // Arrange
        List<CourseEntity> courses = new ArrayList<>();

        doReturn(courses).when(courseRepository).findAll();
        when(CourseMapper.mapToResponse(courseEntity)).thenReturn(courseResponse);

        // Act
        // Assert
        assertEquals(0, courseService.findAll().size());

    }

    @Test
    void CourseService_FindById_ReturnsCorrectObject() {

        // Arrange
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
        var request = mock(CourseAddAndLinkInstructorRequest.class);

        // Sub the necessary methods
        doReturn(Optional.of(instructorEntity)).when(instructorRepository).findById(any());
        when(MapperUtil.map(any(), any())).thenReturn(courseEntity);
        doReturn(courseEntity).when(courseRepository).save(any());
        when(CourseMapper.mapToResponse(any())).thenReturn(courseResponse);

        // Act
        // Assert
        assertNotNull(courseService.saveCourseAndLinkInstructor(request));

    }

    @Test
    void CourseService_SaveCourseAndLinkInstructor_ThrowsInstructorNotFoundException() {

        // Arrange
        var request = new CourseAddAndLinkInstructorRequest(courseDTO, 1);

        // Sub the necessary methods
        doReturn(Optional.empty()).when(instructorRepository).findById(any());

        // Act
        // Assert
        assertThrows(InstructorNotFoundException.class, () -> courseService.saveCourseAndLinkInstructor(request));

    }

    @Test
    void CourseService_Update_UpdatesSuccessfully(){

        // Arrange
        UUID id = UUID.randomUUID();
        CourseDTO courseDTO = new CourseDTO(
                id,
                "new name",
                new Timestamp(System.currentTimeMillis()),
                new Timestamp(System.currentTimeMillis()),
                CourseLevel.Beginner,
                false
        );

        CourseResponse courseResponse = new CourseResponse(courseDTO, null);

        // Stub the necessary methods
        doReturn(Optional.of(courseEntity)).when(courseRepository).findById(any());
        doReturn(courseEntity).when(courseRepository).save(any());
        when(CourseMapper.mapToResponse(any())).thenReturn(courseResponse);

        // Act
        CourseResponse update = courseService.update(courseDTO);

        // Assert
        assertAll(() -> {
            assertNotNull(update);
            assertEquals(id, update.getCourse().getId());
            assertEquals(CourseLevel.Beginner, update.getCourse().getCourseLevel());
            assertEquals(false, update.getCourse().getIsStarted());
        });
    }

    @Test
    void CourseService_Update_ThrowsCourseNotFoundException() {

        // Arrange
        doReturn(Optional.empty()).when(courseRepository).findById(any());

        // Act
        // Assert
        assertThrows(CourseNotFoundException.class, () -> courseService.update(courseDTO));

    }

    @Test
    void CourseService_getNameStartDateEnrolledStudents_ReturnsAll() {

        // Arrange
        List<CourseEntity> entities = Arrays.asList(courseEntity, courseEntity, courseEntity);
        var dto = mock(CourseNameStartDateEnrolledStudentsDTO.class);

        doReturn(entities).when(courseRepository).findAll();
        when(CourseMapper.mapToNameStartDateEnrolledStudents(any())).thenReturn(dto);

        // Act
        // Assert
        assertEquals(3, courseService.getNameStartDateEnrolledStudents().size());

    }

    @Test
    void CourseService_getNameStartDateEnrolledStudents_ReturnsEmptyList() {

        // Arrange
        List<CourseEntity> entities = new ArrayList<>();
        var dto = mock(CourseNameStartDateEnrolledStudentsDTO.class);

        doReturn(entities).when(courseRepository).findAll();
        when(CourseMapper.mapToNameStartDateEnrolledStudents(any())).thenReturn(dto);

        // Act
        // Assert
        assertEquals(0, courseService.getNameStartDateEnrolledStudents().size());

    }

    @Test
    void CourseService_GetCourseLevelEnrolledStudents_ReturnsAll() {

        // Arrange
        var dto = mock(CourseLevelEnrolledStudents.class);

        CourseEntity beginnerCourse = new CourseEntity();
        beginnerCourse.setCourseLevel(CourseLevel.Beginner);

        CourseEntity advancedCourse = new CourseEntity();
        advancedCourse.setCourseLevel(CourseLevel.Advanced);

        List<CourseEntity> entities = Arrays.asList(advancedCourse, beginnerCourse, beginnerCourse, beginnerCourse);

        doReturn(entities).when(courseRepository).findAll();
        when(CourseMapper.mapToCourseLevelEnrolledStudents(any())).thenReturn(dto);

        // Act
        // Assert
        assertEquals(3, courseService.getCourseLevelEnrolledStudents("Beginner").size());
    }

    @Test
    void CourseService_GetCourseLevelEnrolledStudents_ReturnsEmptyList() {

        // Arrange
        var dto = mock(CourseLevelEnrolledStudents.class);
        List<CourseEntity> entities = new ArrayList<>();

        doReturn(entities).when(courseRepository).findAll();
        when(CourseMapper.mapToCourseLevelEnrolledStudents(any())).thenReturn(dto);

        // Act
        // Assert
        assertEquals(0, courseService.getCourseLevelEnrolledStudents("Beginner").size());
    }

    @Test
    void CourseService_GetCourseLevelEnrolledStudents_ThrowsUnknownGenderException() {

        // Arrange
        // Act
        // Assert
        assertThrows(UnknownGenderException.class, () -> courseService.getCourseLevelEnrolledStudents("anything"));
    }

}