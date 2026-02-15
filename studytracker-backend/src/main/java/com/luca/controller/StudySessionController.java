package com.luca.controller;


import com.luca.service.StudySessionService;
import com.luca.dto.StudySessionRequestDTO;
import com.luca.dto.StudySessionResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
public class StudySessionController {

    private final StudySessionService studySessionService;

    public StudySessionController(StudySessionService studySessionService) {
        this.studySessionService = studySessionService;
    }

    //Get all study sessions
    @GetMapping
    public List<StudySessionResponseDTO> getAllStudySessions(){
        return studySessionService.getAllStudySessions();
    }
    
    //create a study session
    @PostMapping
    public StudySessionResponseDTO createStudySession(@Valid @RequestBody StudySessionRequestDTO mStudySessionRequestDTO){
        return studySessionService.createStudySession(mStudySessionRequestDTO);
    }

    //update a study session by ID
    @PutMapping("/{id}")
    public StudySessionResponseDTO updateStudySession(@PathVariable long id, @Valid @RequestBody StudySessionRequestDTO mStudySessionRequestDTO){
        return studySessionService.updateStudySession(id, mStudySessionRequestDTO);
    }

    //get a single study session by ID
    @GetMapping("/{id}")
    public StudySessionResponseDTO getStudySessionById(@PathVariable long id){
        return studySessionService.getStudySessionById(id);
    }

    //get a list of study sessions by their subject ID
    @GetMapping("/subject/{subjectId}")
    public List<StudySessionResponseDTO> getStudySessionsBySubjectId(@PathVariable Long subjectId){
        return studySessionService.getStudySessionsBySubjectId(subjectId);
    }

    //delete a study session by ID
    @DeleteMapping("/{id}")
    public void deleteStudySession(@PathVariable long id){
        studySessionService.deleteStudySession(id);
    }
}
