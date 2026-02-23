package com.luca.service;

import com.luca.model.StudySession;
import com.luca.model.Subject;
import com.luca.repository.StudySessionRepo;
import com.luca.repository.SubjectRepo;
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

    @Mock
    private SubjectRepo subjectRepo;

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
        Subject subject = new Subject();
        testSession.setId(1L);
        subject.setId(1L);
        testSession.setSubject(subject);
        testSession.setDurationMin(45.0);
        testSession.setNotes("Studied arrays");
        testSession.setTimestamp(Instant.now());
    }

    @Test
    void testCreateStudySessionSuccess() {

    // -------- ARRANGE --------
    // Create a mock Subject that would normally come from the database
    Subject subject = new Subject();
    subject.setId(1L);

    // Mock the SubjectRepo to return the subject when findById is called
    when(subjectRepo.findById(1L))
            .thenReturn(Optional.of(subject));

    // Mock the StudySessionRepo save() method to return our test session
    when(studySessionRepo.save(any(StudySession.class)))
            .thenAnswer(invocation -> testSession);

    // -------- ACT --------
    // Call the service method we are testing
    StudySessionResponseDTO result =
            studySessionService.createStudySession(testDto);

    // -------- ASSERT --------
    // Verify the result is not null
    assertNotNull(result);

    // Verify fields were mapped correctly
    assertEquals(1L, result.getId());
    assertEquals(1L, result.getSubjectId());
    assertEquals(45.0, result.getDurationMin());

    // Verify repositories were actually called
    verify(subjectRepo, times(1)).findById(1L);
    verify(studySessionRepo, times(1)).save(any(StudySession.class));
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