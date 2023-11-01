package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.dto.*;
import com.ahmedatef.springboot.restcrud.entity.CourseEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorDetailsEntity;
import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.enums.CourseLevel;
import com.ahmedatef.springboot.restcrud.exception.CourseNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.InstructorDetailsNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.InstructorNotFoundException;
import com.ahmedatef.springboot.restcrud.exception.UnknownGenderException;
import com.ahmedatef.springboot.restcrud.mapper.CourseMapper;
import com.ahmedatef.springboot.restcrud.mapper.InstructorDetailsMapper;
import com.ahmedatef.springboot.restcrud.mapper.InstructorMapper;
import com.ahmedatef.springboot.restcrud.mapper.MapperUtil;
import com.ahmedatef.springboot.restcrud.repository.CourseRepository;
import com.ahmedatef.springboot.restcrud.repository.InstructorDetailsRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstructorDetailsServiceTest {

    @Mock
    private InstructorDetailsRepository instructorDetailsRepository;
    @Mock
    private InstructorRepository instructorRepository;
    @Mock
    private InstructorDetailsEntity instructorDetailsEntity;
    @Mock
    private InstructorEntity instructorEntity;
    @Mock
    private InstructorDetailsDTO instructorDetailsDTO;
    @Mock
    private InstructorDetailsResponse instructorDetailsResponse;
    @InjectMocks
    private InstructorDetailsService instructorDetailsService;

    private static MockedStatic<InstructorDetailsMapper> instructorDetailsMapperMockedStatic;
    private static MockedStatic<MapperUtil> mapperUtilMockedStatic;

    @BeforeAll
    static void createStaticMocks() {
        instructorDetailsMapperMockedStatic = mockStatic(InstructorDetailsMapper.class);
        mapperUtilMockedStatic = mockStatic(MapperUtil.class);
    }

    @AfterAll
    static void destroyStaticMocks() {
        instructorDetailsMapperMockedStatic.close();
        mapperUtilMockedStatic.close();
    }

    @Test
    void InstructorDetailsService_FindAll_ReturnsAllValues() {
        // Arrange
        List<InstructorDetailsEntity> entities = Arrays.asList(instructorDetailsEntity, instructorDetailsEntity, instructorDetailsEntity, instructorDetailsEntity);

        doReturn(entities).when(instructorDetailsRepository).findAll();
        when(InstructorDetailsMapper.mapToResponse(any())).thenReturn(instructorDetailsResponse);

        // Act
        // Assert
        assertEquals(4, instructorDetailsService.findAll().size());

    }

    @Test
    void InstructorDetailsService_FindAll_ReturnsEmptyList() {
        // Arrange
        List<InstructorDetailsEntity> entities = new ArrayList<>();

        doReturn(entities).when(instructorDetailsRepository).findAll();
        when(InstructorDetailsMapper.mapToResponse(any())).thenReturn(instructorDetailsResponse);

        // Act
        // Assert
        assertEquals(0, instructorDetailsService.findAll().size());

    }

    @Test
    void InstructorDetailsService_FindById_ReturnsCorrectObject() {

        // Arrange
        // Create the required objects
        InstructorDetailsResponse response = new InstructorDetailsResponse();
        InstructorDetailsDTO dto = new InstructorDetailsDTO();

        // Set objects' variables
        UUID id = UUID.randomUUID();
        dto.setId(id);
        response.setDetails(dto);

        // Stub the necessary methods
        doReturn(Optional.of(instructorDetailsEntity)).when(instructorDetailsRepository).findById(any());
        when(InstructorDetailsMapper.mapToResponse(any())).thenReturn(response);

        // Act
        InstructorDetailsResponse result = instructorDetailsService.findById(id);

        // Assert
        assertEquals(id, result.getDetails().getId());
    }

    @Test
    void InstructorDetailsService_FindById_ThrowsInstructorDetailsNotFoundException() {

        // Arrange
        // Stub the necessary methods
        doReturn(Optional.empty()).when(instructorDetailsRepository).findById(any());

        // Act
        // Assert
        assertThrows(InstructorDetailsNotFoundException.class, () -> instructorDetailsService.findById(UUID.randomUUID()));
    }

    @Test
    void InstructorDetailsService_DeleteById_DeletesEntity() {

        // Arrange
        // Create a new instructorDetails entity and set its id to a specific value
        // Create a list to act as a database
        // Add the entity to the database
        UUID id = UUID.randomUUID();
        InstructorDetailsEntity entity = new InstructorDetailsEntity();
        entity.setId(id);

        List<InstructorDetailsEntity> entities = new ArrayList<>() {{
            add(entity);
        }};

        // Stub the necessary methods
        doReturn(Optional.of(entity)).when(instructorDetailsRepository).findById(id);
        doAnswer(answer -> {
            entities.remove(answer.getArgument(0, InstructorDetailsEntity.class));
            return null;
        }).when(instructorDetailsRepository).delete(any(InstructorDetailsEntity.class));

        // Act
        // Assert
        assertAll(() -> {
            assertEquals(1, entities.size());
            instructorDetailsService.deleteById(id);
            assertEquals(0, entities.size());
        });
    }

    @Test
    void InstructorDetailsService_DeleteById_ThrowsInstructorDetailsNotFoundException() {

        // Arrange
        // Stub the necessary methods
        doReturn(Optional.empty()).when(instructorDetailsRepository).findById(any());

        // Act
        // Assert
        assertThrows(InstructorDetailsNotFoundException.class, () -> instructorDetailsService.deleteById(UUID.randomUUID()));

    }

    @Test
    void InstructorDetailsService_SveAndLinkInstructor_SavesObjectsCorrectly() {

        // Arrange
        // Sub the necessary methods
        var request = mock(DetailsAddLinkRequest.class);

        doReturn(Optional.of(instructorEntity)).when(instructorRepository).findById(any());
        when(MapperUtil.map(any(), any())).thenReturn(instructorDetailsEntity);
        doReturn(instructorDetailsEntity).when(instructorDetailsRepository).save(any());
        when(InstructorDetailsMapper.mapToResponse(any())).thenReturn(instructorDetailsResponse);

        // Act
        // Assert
        assertNotNull(instructorDetailsService.saveAndLinkInstructor(request));

    }

    @Test
    void InstructorDetailsService_SveAndLinkInstructor_ThrowsInstructorNotFoundException() {

        // Arrange
        // Sub the necessary methods
        var request = mock(DetailsAddLinkRequest.class);
        doReturn(Optional.empty()).when(instructorRepository).findById(any());

        // Act
        // Assert
        assertThrows(InstructorNotFoundException.class, () -> instructorDetailsService.saveAndLinkInstructor(request));

    }

    @Test
    void InstructorDetailsService_Update_UpdatesSuccessfully(){

        // Arrange
        UUID id = UUID.randomUUID();
        int instructorId = 1;
        InstructorDetailsDTO dto = new InstructorDetailsDTO(
                id,
                "new channel",
                null
        );

        InstructorDetailsResponse response = new InstructorDetailsResponse(instructorId, "first", "last", dto);

        // Stub the necessary methods
        doReturn(Optional.of(instructorDetailsEntity)).when(instructorDetailsRepository).findById(any());
        doReturn(instructorDetailsEntity).when(instructorDetailsRepository).save(any());
        when(InstructorDetailsMapper.mapToResponse(any())).thenReturn(response);

        // Act
        InstructorDetailsResponse update = instructorDetailsService.update(dto);

        // Assert
        assertAll(() -> {
            assertNotNull(update);
            assertEquals(id, update.getDetails().getId());
            assertEquals("new channel", update.getDetails().getYoutubeChannel());
        });
    }

    @Test
    void InstructorDetailsService_Update_ThrowsInstructorDetailsNotFoundException() {

        // Arrange
        doReturn(Optional.empty()).when(instructorDetailsRepository).findById(any());

        // Act
        // Assert
        assertThrows(InstructorDetailsNotFoundException.class, () -> instructorDetailsService.update(instructorDetailsDTO));

    }
}