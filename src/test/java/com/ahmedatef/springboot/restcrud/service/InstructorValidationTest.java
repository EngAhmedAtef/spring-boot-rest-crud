package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.entity.InstructorEntity;
import com.ahmedatef.springboot.restcrud.repository.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class InstructorValidationTest {

    @Mock
    private InstructorRepository repository;
    @Mock
    private InstructorEntity instructorEntity;
    @InjectMocks
    private InstructorValidation service;

    @Test
    void InstructorValidation_ValidatePhoneNumber_ReturnsTrue() {
        // Arrange
        doReturn(instructorEntity).when(repository).findByPhoneNumber(any());

        // Act
        // Assert
        assertTrue(service.validatePhoneNumber(""));
    }

    @Test
    void InstructorValidation_ValidatePhoneNumber_ReturnsFalse() {
        // Arrange
        doReturn(null).when(repository).findByPhoneNumber(any());

        // Act
        // Assert
        assertFalse(service.validatePhoneNumber(""));
    }

    @Test
    void InstructorValidation_ValidateEmailAddress_ReturnsTrue() {
        // Arrange
        // Act
        // Assert
        assertAll(() -> {
            assertTrue(service.validateEmailAddress("username@domain.com"));
            assertTrue(service.validateEmailAddress("user.name@domain.com"));
            assertTrue(service.validateEmailAddress("user-name@domain.com"));
            assertTrue(service.validateEmailAddress("username@domain.co.in"));
            assertTrue(service.validateEmailAddress("user_name@domain.com"));
        });
    }

    @Test
    void InstructorValidation_ValidateEmailAddress_ReturnsFalse() {
        // Arrange
        // Act
        // Assert
        assertAll(() -> {
            assertFalse(service.validateEmailAddress("username.@domain.com"));
            assertFalse(service.validateEmailAddress(".user.name@domain.com"));
            assertFalse(service.validateEmailAddress("user-name@domain.com."));
            assertFalse(service.validateEmailAddress("username@.com"));
        });
    }

    @Test
    void InstructorValidation_ValidateYoutubeChannel_ReturnsTrue() {
        // Arrange
        String youtubeChannel = "My Channel";

        // Act
        // Assert
        assertTrue(service.validateYoutubeChannel(youtubeChannel));
    }

    @Test
    void InstructorValidation_ValidateYoutubeChannel_ReturnsFalse() {
        // Arrange
        // Act
        // Assert
        assertAll(() -> {
            assertFalse(service.validateYoutubeChannel(null));
            assertFalse(service.validateYoutubeChannel(""));
            assertFalse(service.validateYoutubeChannel("    "));
        });
    }
}