package com.luca.service;

import com.luca.model.Subject;
import com.luca.repository.SubjectRepo;
import com.luca.dto.SubjectRequestDTO;
import com.luca.dto.SubjectResponseDTO;

import com.luca.exception.ResourceNotFoundException;
import com.luca.exception.InvalidRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import java.util.List;

import java.util.stream.Collectors;


@Service
public class SubjectService {
    private static final Logger logger = LoggerFactory.getLogger(SubjectService.class);

    private final SubjectRepo subjectRepo;


    public SubjectService(SubjectRepo mSubjectRepo){
        this.subjectRepo = mSubjectRepo;
    }

    //returning a list of all subjects (converted to DTOs)
    public List<SubjectResponseDTO> getAllSubjects(){
       logger.info("Fetching all subjects");
        return subjectRepo.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //creating a subject from DTO
    public SubjectResponseDTO createSubject(SubjectRequestDTO mSubjectRequestDTO){
        if (mSubjectRequestDTO.getName() == null || mSubjectRequestDTO.getName().trim().isEmpty()) {
            logger.info("Failed to create subject: name is empty");
            throw new InvalidRequestException("Subject name cannot be empty");
        }
        logger.info("Creating subject with name: {}", mSubjectRequestDTO.getName());
        Subject subject = new Subject();
        subject.setName(mSubjectRequestDTO.getName());
        //the created at variable does not need to be set here, it stays the same as when the object is created
        Subject savedSubject = subjectRepo.save(subject);
        logger.info("Subject created with ID: {}", savedSubject.getId());
        return convertToResponseDTO(savedSubject);
    }

    //deleting a subject by its ID
    public SubjectResponseDTO deleteSubject(long mSubjectId){
        logger.info("Deleting subject with ID: {}", mSubjectId);
        Subject subject = subjectRepo.findById(mSubjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + mSubjectId));
        subjectRepo.deleteById(mSubjectId);
        logger.info("Subject deleted with ID: {}", mSubjectId);
        return convertToResponseDTO(subject);
    }

    //getting a subject by its ID (converted to DTO)
    public SubjectResponseDTO getSubjectByID(long mSubjectId){
        logger.info("Fetching subject with ID: {}", mSubjectId);
        Subject subject = subjectRepo.findById(mSubjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + mSubjectId));
        logger.info("Subject fetched with ID: {}", mSubjectId);
        return convertToResponseDTO(subject);
    }

    //updating a subject by its ID from DTO
    public SubjectResponseDTO updateSubject(long mSubjectId, SubjectRequestDTO mSubjectRequestDTO){
        logger.info("Updating subject with ID: {}", mSubjectId);
        if (mSubjectRequestDTO.getName() == null || mSubjectRequestDTO.getName().trim().isEmpty()) {
            throw new InvalidRequestException("Subject name cannot be empty");
        }
        Subject subject = subjectRepo.findById(mSubjectId)
                .orElseThrow(() -> new ResourceNotFoundException("Subject not found with id: " + mSubjectId));
        subject.setName(mSubjectRequestDTO.getName());
        Subject updatedSubject = subjectRepo.save(subject);
        logger.info("Subject updated with ID: {}", mSubjectId);
        return convertToResponseDTO(updatedSubject);
    }

    //helper method: convert Entity to Response DTO
    private SubjectResponseDTO convertToResponseDTO(Subject subject) {
        SubjectResponseDTO dto = new SubjectResponseDTO();
        dto.setId(subject.getId());
        dto.setName(subject.getName());
        dto.setCreatedAt(subject.getCreatedAt());
        return dto;
    }
}

