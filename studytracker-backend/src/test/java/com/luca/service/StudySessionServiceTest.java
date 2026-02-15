package com.luca.service;

import com.luca.model.StudySession;
import com.luca.repository.StudySessionRepo;
import com.luca.dto.StudySessionRequestDTO;
import com.luca.dto.StudySessionResponseDTO;
import com.luca.exception.ResourceNotFoundException;
import com.luca.exception.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudySessionServiceTest {

    @Mock
    private StudySessionRepo studySessionRepo;

    @InjectMocks
    private StudySessionService studySessionService;

    private StudySessionRequestDTO testDto;
    private StudySession testSession;

    @BeforeEach
    void setUp() {
        testDto = new StudySessionRequestDTO();
        testDto.setSubjectId(1L);
        testDto.setDurationMin(45.0);
        testDto.setNotes("Studied arrays");
        testDto.setTimestamp(Instant.now());

        testSession = new StudySession();
        testSession.setId(1L);
        testSession.setSubjectId(1L);
        testSession.setDurationMin(45.0);
        testSession.setNotes("Studied arrays");
        testSession.setTimestamp(Instant.now());
    }

    @Test
    void testCreateStudySessionSuccess() {
        // Arrange
        when(studySessionRepo.save(any(StudySession.class))).thenAnswer(invocation -> testSession);

        // Act
        StudySessionResponseDTO result = studySessionService.createStudySession(testDto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(1L, result.getSubjectId());
        assertEquals(45.0, result.getDurationMin());
    }

    @Test
    void testCreateStudySessionWithNegativeDuration() {
        // Arrange: DTO with negative duration
        StudySessionRequestDTO invalidDto = new StudySessionRequestDTO();
        invalidDto.setSubjectId(1L);
        invalidDto.setDurationMin(-5);

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> {
            studySessionService.createStudySession(invalidDto);
        });
    }

    @Test
    void testCreateStudySessionWithoutSubjectId() {
        // Arrange
        StudySessionRequestDTO invalidDto = new StudySessionRequestDTO();
        invalidDto.setDurationMin(45);

        // Act & Assert
        assertThrows(InvalidRequestException.class, () -> {
            studySessionService.createStudySession(invalidDto);
        });
    }

    @Test
    void testGetStudySessionByIdSuccess() {
        // Arrange
        when(studySessionRepo.findById(1L)).thenReturn(Optional.of(testSession));

        // Act
        StudySessionResponseDTO result = studySessionService.getStudySessionById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testGetStudySessionByIdNotFound() {
        // Arrange
        when(studySessionRepo.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            studySessionService.getStudySessionById(999L);
        });
    }

    @Test
    void testDeleteStudySessionSuccess() {
        // Arrange
        when(studySessionRepo.findById(1L)).thenReturn(Optional.of(testSession));
        doNothing().when(studySessionRepo).deleteById(1L);

        // Act
        StudySessionResponseDTO result = studySessionService.deleteStudySession(1L);

        // Assert
        assertNotNull(result);
        verify(studySessionRepo, times(1)).deleteById(1L);
    }
}