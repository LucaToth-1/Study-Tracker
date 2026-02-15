package com.luca.controller;


import com.luca.service.SubjectService;
import com.luca.dto.SubjectRequestDTO;
import com.luca.dto.SubjectResponseDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
@CrossOrigin(origins = "http://localhost:3000") // Allow React frontend
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService mSubjectService) {
        this.subjectService = mSubjectService;
    }

    //Get all subjects
    @GetMapping
    public List<SubjectResponseDTO> getAllSubjects(){
        return subjectService.getAllSubjects();
    }

    //Create a new subject
    @PostMapping
    public SubjectResponseDTO createSubject(@Valid @RequestBody SubjectRequestDTO mSubjectRequestDTO){
        return subjectService.createSubject(mSubjectRequestDTO);
    }

    //Get a single subject by ID
    @GetMapping("/{id}")
    public SubjectResponseDTO getSubjectById(@PathVariable long mId){
        return subjectService.getSubjectByID(mId);
    }

    //Update a subject by ID
    @PutMapping("/{id}")
    public SubjectResponseDTO updateSubject(@PathVariable long id, @Valid @RequestBody SubjectRequestDTO mSubjectRequestDTO){
         return subjectService.updateSubject(id, mSubjectRequestDTO);
        }
    

    //Delete a subject by ID
    @DeleteMapping("/{id}")
    public SubjectResponseDTO deleteSubject(@PathVariable long mId){
        return subjectService.deleteSubject(mId);
    }
}

