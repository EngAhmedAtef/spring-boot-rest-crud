package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.InstructorAndEnrolledStudentsDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorNameAndCourseNamesDTO;
import com.ahmedatef.springboot.restcrud.dto.InstructorResponse;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.exception.InstructorAlreadyExistsException;
import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.InvalidEmailAddressException;
import com.ahmedatef.springboot.restcrud.mapper.InstructorMapper;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTest {

    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private InstructorEntity instructorEntity;
    @Mock
    private InstructorDTO instructorDTO;
    @Mock
    private InstructorResponse instructorResponse;
    @InjectMocks
    @Spy
    private InstructorService instructorService;

    private static MockedStatic<InstructorMapper> instructorMapperMockedStatic;
    private static MockedStatic<MapperUtil> mapperUtilMockedStatic;

    @BeforeAll
    static void createStaticMocks() {
        instructorMapperMockedStatic = mockStatic(InstructorMapper.class);
        mapperUtilMockedStatic = mockStatic(MapperUtil.class);
    }

    @AfterAll
    static void destroyStaticMocks() {
        instructorMapperMockedStatic.close();
        mapperUtilMockedStatic.close();
    }

    @Test
    void InstructorService_FindAll_ReturnsAllValues() {
        // Arrange
        List<InstructorEntity> entities = Arrays.asList(instructorEntity, instructorEntity, instructorEntity, instructorEntity);

        doReturn(entities).when(instructorRepository).findAll();
        when(InstructorMapper.mapToResponse(any())).thenReturn(instructorResponse);

        // Act
        // Assert
        assertEquals(4, instructorService.findAll().size());

    }

    @Test
    void InstructorService_FindAll_ReturnsEmptyList() {
        // Arrange
        List<InstructorEntity> entities = new ArrayList<>();

        doReturn(entities).when(instructorRepository).findAll();
        when(InstructorMapper.mapToResponse(any())).thenReturn(instructorResponse);

        // Act
        // Assert
        assertEquals(0, instructorService.findAll().size());

    }

    @Test
    void InstructorService_FindById_ReturnsCorrectObject() {

        // Arrange
        // Create the required objects
        InstructorResponse response = new InstructorResponse();
        InstructorDTO dto = new InstructorDTO();

        // Set objects' variables
        int id = 1;
        dto.setId(id);
        response.setInstructor(dto);

        // Stub the necessary methods
        doReturn(Optional.of(instructorEntity)).when(instructorRepository).findById(any());
        when(InstructorMapper.mapToResponse(any())).thenReturn(response);

        // Act
        InstructorResponse result = instructorService.findById(id);

        // Assert
        assertEquals(id, result.getInstructor().getId());
    }

    @Test
    void InstructorService_FindById_ThrowsInstructorNotFoundException() {

        // Arrange
        // Stub the necessary methods
        doReturn(Optional.empty()).when(instructorRepository).findById(any());

        // Act
        // Assert
        assertThrows(InstructorNotFoundException.class, () -> instructorService.findById(1));
    }

    @Test
    void InstructorService_DeleteById_DeletesEntity() {

        // Arrange
        // Create a new instructor entity and set its id to a specific value
        // Create a list to act as a database
        // Add the entity to the database
        int id = 1;
        InstructorEntity entity = new InstructorEntity();
        entity.setId(id);

        List<InstructorEntity> entities = new ArrayList<>() {{
            add(entity);
        }};

        // Stub the necessary methods
        doReturn(Optional.of(entity)).when(instructorRepository).findById(id);
        doAnswer(answer -> {
            entities.remove(answer.getArgument(0, InstructorEntity.class));
            return null;
        }).when(instructorRepository).delete(any(InstructorEntity.class));

        // Act
        // Assert
        assertAll(() -> {
            assertEquals(1, entities.size());
            instructorService.deleteById(id);
            assertEquals(0, entities.size());
        });

    }

    @Test
    void InstructorService_DeleteById_ThrowsInstructorNotFoundException() {

        // Arrange
        // Stub the necessary methods
        doReturn(Optional.empty()).when(instructorRepository).findById(any());

        // Act
        // Assert
        assertThrows(InstructorNotFoundException.class, () -> instructorService.deleteById(1));

    }

    @Test
    void InstructorService_Save_SavesObjectsCorrectly() {

        // Arrange
        // Sub the necessary methods
        when(MapperUtil.map(any(), any())).thenReturn(instructorEntity);
        doReturn(instructorEntity).when(instructorRepository).save(any());
        when(InstructorMapper.mapToResponse(any())).thenReturn(instructorResponse);

//        when(instructorService.validatePhoneNumber(any())).thenReturn(true);
//        when(instructorService.validateEmailAddress(any())).thenReturn(true);

        doReturn(true).when(instructorService).validatePhoneNumber(any());
        doReturn(true).when(instructorService).validateEmailAddress(any());

        // Act
        // Assert
        assertNotNull(instructorService.save(instructorDTO));

    }

    @Test
    void InstructorService_Save_ThrowsInstructorAlreadyExistsException() {

        // Arrange
        // Sub the necessary methods
        doReturn(false).when(instructorService).validatePhoneNumber(any());

        // Act
        // Assert
        assertThrows(InstructorAlreadyExistsException.class, () -> instructorService.save(instructorDTO));

    }

    @Test
    void InstructorService_Save_ThrowsInvalidEmailAddressException() {

        // Arrange
        // Sub the necessary methods
        doReturn(true).when(instructorService).validatePhoneNumber(any());
        doReturn(false).when(instructorService).validateEmailAddress(any());

        // Act
        // Assert
        assertThrows(InvalidEmailAddressException.class, () -> instructorService.save(instructorDTO));

    }

    @Test
    void InstructorService_Update_UpdatesSuccessfully(){

        // Arrange
        int id = 1;
        InstructorDTO dto = new InstructorDTO(
                id,
                "new first",
                "new last",
                "new email",
                "new number",
                "new title"
        );

        InstructorResponse response = new InstructorResponse(dto, null, null);

        // Stub the necessary methods
        doReturn(Optional.of(instructorEntity)).when(instructorRepository).findById(any());
        doReturn(instructorEntity).when(instructorRepository).save(any());
        when(InstructorMapper.mapToResponse(any())).thenReturn(response);
        doReturn(true).when(instructorService).validatePhoneNumber(any());
        doReturn(true).when(instructorService).validateEmailAddress(any());

        // Act
        InstructorResponse update = instructorService.update(dto);

        // Assert
        assertAll(() -> {
            assertNotNull(update);
            assertEquals(id, update.getInstructor().getId());
            assertEquals("new first", update.getInstructor().getFirstName());
            assertEquals("new last", update.getInstructor().getLastName());
        });
    }

    @Test
    void InstructorService_Update_ThrowsInstructorNotFoundException() {

        // Arrange
        doReturn(Optional.empty()).when(instructorRepository).findById(any());
        doReturn(true).when(instructorService).validatePhoneNumber(any());
        doReturn(true).when(instructorService).validateEmailAddress(any());

        // Act
        // Assert
        assertThrows(InstructorNotFoundException.class, () -> instructorService.update(instructorDTO));

    }

    @Test
    void InstructorService_Update_ThrowsInstructorAlreadyExistsException() {

        // Arrange
        doReturn(false).when(instructorService).validatePhoneNumber(any());

        // Act
        // Assert
        assertThrows(InstructorAlreadyExistsException.class, () -> instructorService.update(instructorDTO));

    }

    @Test
    void InstructorService_Update_ThrowsInvalidEmailAddressException() {

        // Arrange
        doReturn(true).when(instructorService).validatePhoneNumber(any());
        doReturn(false).when(instructorService).validateEmailAddress(any());

        // Act
        // Assert
        assertThrows(InvalidEmailAddressException.class, () -> instructorService.update(instructorDTO));

    }

    @Test
    void InstructorService_getInstructorAndCourses_ReturnsAll() {

        // Arrange
        var dto = mock(InstructorNameAndCourseNamesDTO.class);
        List<InstructorNameAndCourseNamesDTO> entities = Arrays.asList(dto, dto, dto);

        doReturn(entities).when(instructorRepository).getInstructorCoursesNames();

        // Act
        // Assert
        assertEquals(3, instructorService.getInstructorAndCourses().size());

    }

    @Test
    void InstructorService_getInstructorAndCourses_ReturnsEmptyList() {

        // Arrange
        List<InstructorNameAndCourseNamesDTO> entities = new ArrayList<>();

        doReturn(entities).when(instructorRepository).getInstructorCoursesNames();

        // Act
        // Assert
        assertEquals(0, instructorService.getInstructorAndCourses().size());

    }

    @Test
    void InstructorService_getInstructorAndEnrolledStudents_ReturnsAll() {

        // Arrange
        var dto = mock(InstructorAndEnrolledStudentsDTO.class);
        List<InstructorEntity> entities = Arrays.asList(instructorEntity, instructorEntity, instructorEntity, instructorEntity);

        doReturn(entities).when(instructorRepository).findAll();
        when(InstructorMapper.mapToInstructorAndEnrolledStudents(any())).thenReturn(dto);

        // Act
        // Assert
        assertEquals(4, instructorService.getInstructorAndEnrolledStudents().size());
    }

    @Test
    void InstructorService_getInstructorAndEnrolledStudents_ReturnsEmptyList() {

        // Arrange
        var dto = mock(InstructorAndEnrolledStudentsDTO.class);
        List<InstructorEntity> entities = new ArrayList<>();

        doReturn(entities).when(instructorRepository).findAll();
        when(InstructorMapper.mapToInstructorAndEnrolledStudents(any())).thenReturn(dto);

        // Act
        // Assert
        assertEquals(0, instructorService.getInstructorAndEnrolledStudents().size());
    }

}