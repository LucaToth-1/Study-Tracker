package com.luca.dto;

import java.time.Instant;

public class StudySessionResponseDTO {
    private Long id;
    private Long subjectId;
    private double durationMin;
    private String subjectName;
    private String notes;
    private Instant timestamp;

    //Constructors
    public StudySessionResponseDTO() {
    }

    public StudySessionResponseDTO(Long mId, Long mSubjectId, double mDurationMin, String mSubjectName, String mNotes, Instant mTimestamp) {
        this.id = mId;
        this.subjectId = mSubjectId;
        this.durationMin = mDurationMin;
        this.subjectName = mSubjectName;
        this.notes = mNotes;
        this.timestamp = mTimestamp;
    }

    //getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long mId) {
        this.id = mId;
    }

    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long mSubjectId){
        this.subjectId = mSubjectId;
    }

    public double getDurationMin() {
        return durationMin;
    }
    public void setDurationMin(double mDurationMin){
        this.durationMin = mDurationMin;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String mNotes){
        this.notes = mNotes;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant mTimestamp) {
        this.timestamp = mTimestamp;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String mSubjectName) {
        this.subjectName = mSubjectName;
    }
}
