package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.StudentDTO;
import com.ahmedatef.springboot.restcrud.dto.StudentResponse;
import com.ahmedatef.springboot.restcrud.entity.StudentEntity;
import com.ahmedatef.springboot.restcrud.exception.StudentNotFoundException;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.mapper.StudentMapper;
import com.ahmedatef.springboot.restcrud.repository.StudentRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentEntity studentEntity;
    @Mock
    private StudentDTO studentDTO;
    @Mock
    private StudentResponse studentResponse;
    @InjectMocks
    private StudentService studentService;

    private static MockedStatic<StudentMapper> studentMapperMockedStatic;
    private static MockedStatic<MapperUtil> mapperUtilMockedStatic;

    @BeforeAll
    static void createStaticMocks() {
        studentMapperMockedStatic = mockStatic(StudentMapper.class);
        mapperUtilMockedStatic = mockStatic(MapperUtil.class);
    }

    @AfterAll
    static void destroyStaticMocks() {
        studentMapperMockedStatic.close();
        mapperUtilMockedStatic.close();
    }

    @Test
    void StudentService_FindAll_ReturnsAllValues() {
        // Arrange
        List<StudentEntity> entities = Arrays.asList(studentEntity, studentEntity, studentEntity, studentEntity);

        doReturn(entities).when(studentRepository).findAll();
        when(StudentMapper.mapToResponse(any())).thenReturn(studentResponse);

        // Act
        // Assert
        assertEquals(4, studentService.findAll().size());

    }

    @Test
    void StudentService_FindAll_ReturnsEmptyList() {
        // Arrange
        List<StudentEntity> entities = new ArrayList<>();

        doReturn(entities).when(studentRepository).findAll();
        when(StudentMapper.mapToResponse(any())).thenReturn(studentResponse);

        // Act
        // Assert
        assertEquals(0, studentService.findAll().size());

    }

    @Test
    void StudentService_FindById_ReturnsCorrectObject() {

        // Arrange
        // Create the required objects
        StudentResponse response = new StudentResponse();
        StudentDTO dto = new StudentDTO();

        // Set objects' variables
        UUID id = UUID.randomUUID();
        dto.setId(id);
        response.setStudent(dto);

        // Stub the necessary methods
        doReturn(Optional.of(studentEntity)).when(studentRepository).findById(any());
        when(StudentMapper.mapToResponse(any())).thenReturn(response);

        // Act
        StudentResponse result = studentService.findById(id);

        // Assert
        assertEquals(id, result.getStudent().getId());
    }

    @Test
    void StudentService_FindById_ThrowsStudentNotFoundException() {

        // Arrange
        // Stub the necessary methods
        doReturn(Optional.empty()).when(studentRepository).findById(any());

        // Act
        // Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.findById(UUID.randomUUID()));
    }

    @Test
    void StudentService_DeleteById_DeletesEntity() {

        // Arrange
        // Create a new instructorDetails entity and set its id to a specific value
        // Create a list to act as a database
        // Add the entity to the database
        UUID id = UUID.randomUUID();
        StudentEntity entity = new StudentEntity();
        entity.setId(id);

        List<StudentEntity> entities = new ArrayList<>() {{
            add(entity);
        }};

        // Stub the necessary methods
        doReturn(Optional.of(entity)).when(studentRepository).findById(id);
        doAnswer(answer -> {
            entities.remove(answer.getArgument(0, StudentEntity.class));
            return null;
        }).when(studentRepository).delete(any(StudentEntity.class));

        // Act
        // Assert
        assertAll(() -> {
            assertEquals(1, entities.size());
            studentService.deleteById(id);
            assertEquals(0, entities.size());
        });
    }

    @Test
    void StudentService_DeleteById_ThrowsStudentNotFoundException() {

        // Arrange
        // Stub the necessary methods
        doReturn(Optional.empty()).when(studentRepository).findById(any());

        // Act
        // Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.deleteById(UUID.randomUUID()));

    }

    @Test
    void StudentService_save_SavesObjectsCorrectly() {

        // Arrange
        // Sub the necessary methods
        when(MapperUtil.map(any(), any())).thenReturn(studentEntity);
        doReturn(studentEntity).when(studentRepository).save(any());
        when(StudentMapper.mapToResponse(any())).thenReturn(studentResponse);

        // Act
        // Assert
        assertNotNull(studentService.save(studentDTO));

    }

    @Test
    void StudentService_Update_UpdatesSuccessfully(){

        // Arrange
        UUID id = UUID.randomUUID();
        StudentDTO dto = new StudentDTO(
                id,
                "new first",
                "new last",
                5,
                null,
                "new email",
                "new number",
                "new id"
        );

        StudentResponse response = new StudentResponse(dto, null);

        // Stub the necessary methods
        doReturn(Optional.of(studentEntity)).when(studentRepository).findById(any());
        doReturn(studentEntity).when(studentRepository).save(any());
        when(StudentMapper.mapToResponse(any())).thenReturn(response);

        // Act
        StudentResponse update = studentService.update(dto);

        // Assert
        assertAll(() -> {
            assertNotNull(update);
            assertEquals(id, update.getStudent().getId());
            assertEquals("new first", update.getStudent().getFirstName());
        });
    }

    @Test
    void StudentService_Update_ThrowsStudentNotFoundException() {

        // Arrange
        doReturn(Optional.empty()).when(studentRepository).findById(any());

        // Act
        // Assert
        assertThrows(StudentNotFoundException.class, () -> studentService.update(studentDTO));

    }
}