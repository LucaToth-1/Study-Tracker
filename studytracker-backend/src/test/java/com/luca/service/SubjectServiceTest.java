package com.luca.service;

import com.luca.model.Subject;
import com.luca.repository.SubjectRepo;
import com.luca.dto.SubjectRequestDTO;
import com.luca.dto.SubjectResponseDTO;
import com.luca.exception.ResourceNotFoundException;
import com.luca.exception.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)  // Enables Mockito
public class SubjectServiceTest {

    @Mock
    private SubjectRepo subjectRepo;  // Mock the repository

    @InjectMocks
    private SubjectService subjectService;  // Inject mocked repo into service

    private SubjectRequestDTO testDto;
    private Subject testSubject;

    @BeforeEach
    void setUp() {
        // Setup test data before each test
        testDto = new SubjectRequestDTO();
        testDto.setName("Math");

        testSubject = new Subject();
        testSubject.setId(1L);
        testSubject.setName("Math");
        //testSubject.setCreatedAt(Instant.now());
    }

    // ============ TESTS ============

    @Test
    void testCreateSubjectSuccess() {
        // Arrange: Mock the repository to return a subject
        when(subjectRepo.save(any(Subject.class))).thenReturn(testSubject);

        // Act: Call the service
        SubjectResponseDTO result = subjectService.createSubject(testDto);

        // Assert: Verify the result
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Math", result.getName());
        
        // Verify repository was called
        verify(subjectRepo, times(1)).save(any(Subject.class));
    }

    @Test
    void testCreateSubjectWithEmptyName() {
        // Arrange: DTO with empty name
        SubjectRequestDTO emptyDto = new SubjectRequestDTO();
        emptyDto.setName("");

        // Act & Assert: Should throw exception
        assertThrows(InvalidRequestException.class, () -> {
            subjectService.createSubject(emptyDto);
        });
    }

    @Test
    void testGetSubjectByIdSuccess() {
        // Arrange: Mock repository to return a subject
        when(subjectRepo.findById(1L)).thenReturn(Optional.of(testSubject));

        // Act: Call the service
        SubjectResponseDTO result = subjectService.getSubjectByID(1L);

        // Assert: Verify result
        assertNotNull(result);
        assertEquals("Math", result.getName());
    }

    @Test
    void testGetSubjectByIdNotFound() {
        // Arrange: Mock repository to return empty
        when(subjectRepo.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert: Should throw exception
        assertThrows(ResourceNotFoundException.class, () -> {
            subjectService.getSubjectByID(999L);
        });
    }

    @Test
    void testUpdateSubjectSuccess() {
        // Arrange: Mock repository
        when(subjectRepo.findById(1L)).thenReturn(Optional.of(testSubject));
        
        // Create update DTO
        SubjectRequestDTO updateDto = new SubjectRequestDTO();
        updateDto.setName("Advanced Math");
        
        // Create updated subject to return from save
        Subject updatedSubject = new Subject();
        updatedSubject.setId(1L);
        updatedSubject.setName("Advanced Math");
        
        when(subjectRepo.save(any(Subject.class))).thenReturn(updatedSubject);

        // Act: Call update
        SubjectResponseDTO result = subjectService.updateSubject(1L, updateDto);

        // Assert: Verify result
        assertNotNull(result);
        assertEquals("Advanced Math", result.getName());  // Verify the updated name
    }

    @Test
    void testDeleteSubjectSuccess() {
    // Arrange
    when(subjectRepo.findById(1L)).thenReturn(Optional.of(testSubject));
    doNothing().when(subjectRepo).delete(testSubject);

    // Act
    subjectService.deleteSubject(1L);

    // Assert
    verify(subjectRepo, times(1)).delete(testSubject);
    }

    @Test
    void testGetAllSubjects() {
        // Arrange: Mock repository to return list
        when(subjectRepo.findAll()).thenReturn(java.util.List.of(testSubject));

        // Act: Call the service
        var results = subjectService.getAllSubjects();

        // Assert: Verify results
        assertNotNull(results);
        assertEquals(1, results.size());
    }
}