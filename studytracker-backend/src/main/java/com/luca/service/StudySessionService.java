package com.luca.service;

import com.luca.model.StudySession;
import com.luca.repository.SubjectRepo;
import com.luca.model.Subject;
import com.luca.repository.StudySessionRepo;
import com.luca.dto.StudySessionRequestDTO;
import com.luca.dto.StudySessionResponseDTO;

import com.luca.exception.ResourceNotFoundException;
import com.luca.exception.InvalidRequestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class StudySessionService {
    private static final Logger logger = LoggerFactory.getLogger(StudySessionService.class);

    private final StudySessionRepo studySessionRepo;
    private final SubjectRepo subjectRepo;
    
    public StudySessionService(StudySessionRepo mStudySessionRepo, SubjectRepo mSubjectRepo){
        this.studySessionRepo = mStudySessionRepo;
        this.subjectRepo = mSubjectRepo;
    }

    //Returning a list of all study sessions
    public List<StudySessionResponseDTO> getAllStudySessions(){
        logger.info("Fetching all study sessions");
        return studySessionRepo.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    //Creating a study session from DTO
    public StudySessionResponseDTO createStudySession(StudySessionRequestDTO mStudySessionRequestDTO){
        logger.info("Creating study session for subject ID: {}", mStudySessionRequestDTO.getSubjectId());
        if (mStudySessionRequestDTO.getSubjectId() == null) {
            throw new InvalidRequestException("Subject ID is required");
        }
        if (mStudySessionRequestDTO.getDurationMin() <= 0) {
            throw new InvalidRequestException("Duration must be greater than 0");
        }
        if (mStudySessionRequestDTO.getNotes().length() > 256){
            throw new InvalidRequestException("Character Limit is 256 characters");
        }
        StudySession session = new StudySession();
        Subject subject = subjectRepo.findById(mStudySessionRequestDTO.getSubjectId())
        .orElseThrow(() -> new ResourceNotFoundException(
                "Subject not found with id: " + mStudySessionRequestDTO.getSubjectId()));

session.setSubject(subject);
        session.setDurationMin(mStudySessionRequestDTO.getDurationMin());
        session.setNotes(mStudySessionRequestDTO.getNotes());
        session.setTimestamp(mStudySessionRequestDTO.getTimestamp());
        StudySession savedSession = studySessionRepo.save(session);
        logger.info("Study session created with ID: {}", savedSession.getId());
        return convertToResponseDTO(savedSession);
    }

    //Deleting a study session by its ID
    public StudySessionResponseDTO deleteStudySession(long mStudySessionId){
        logger.info("Deleting study session with ID: {}", mStudySessionId);
        StudySession session = studySessionRepo.findById(mStudySessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Study session not found with id: " + mStudySessionId));
        studySessionRepo.deleteById(mStudySessionId);
        logger.info("Study session deleted with ID: {}", mStudySessionId);
        return convertToResponseDTO(session);
    }
    
    //Getting study sessions by subject ID
    public List<StudySessionResponseDTO> getStudySessionsBySubjectId(Long mSubjectId){
        logger.info("Fetching study sessions for subject ID: {}", mSubjectId);
        return studySessionRepo.findBySubject_Id(mSubjectId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    //Updating a study session by its ID
    public StudySessionResponseDTO updateStudySession(long id, StudySessionRequestDTO mStudySessionRequestDTO){
        logger.info("Updating study session with ID: {}", id);
        if (mStudySessionRequestDTO.getDurationMin() <= 0) {
            throw new InvalidRequestException("Duration must be greater than 0");
        }
        StudySession session = studySessionRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Study session not found with id: " + id));
        Subject subject = subjectRepo.findById(mStudySessionRequestDTO.getSubjectId())
        .orElseThrow(() -> new ResourceNotFoundException(
                "Subject not found with id: " + mStudySessionRequestDTO.getSubjectId()));
                session.setSubject(subject);
        session.setDurationMin(mStudySessionRequestDTO.getDurationMin());
        session.setNotes(mStudySessionRequestDTO.getNotes());
        session.setTimestamp(mStudySessionRequestDTO.getTimestamp());
        StudySession updatedSession = studySessionRepo.save(session);
        logger.info("Study session updated with ID: {}", id);
        return convertToResponseDTO(updatedSession);
    }

    //Getting a single study session by its ID
    public StudySessionResponseDTO getStudySessionById(long mStudySessionId){
        logger.info("Fetching study session with ID: {}", mStudySessionId);
        StudySession session = studySessionRepo.findById(mStudySessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Study session not found with id: " + mStudySessionId));
        logger.info("Study session fetched with ID: {}", mStudySessionId);
        return convertToResponseDTO(session);
    }
    
    //Helper method: convert Entity to Response DTO
    private StudySessionResponseDTO convertToResponseDTO(StudySession session) {
        StudySessionResponseDTO dto = new StudySessionResponseDTO();
        dto.setId(session.getId());
        dto.setSubjectId(session.getSubject().getId());
        dto.setDurationMin(session.getDurationMin());
        dto.setNotes(session.getNotes());
        dto.setTimestamp(session.getTimestamp());
        return dto;
    }
}

