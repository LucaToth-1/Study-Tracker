package com.luca.dto;

import jakarta.validation.constraints.*;
import java.time.Instant;

public class StudySessionRequestDTO {
    
    @NotNull(message = "Subject ID is required")
    private Long subjectId;

    @Positive(message = "Duration must be greater than 0")
    @Max(value = 1440, message = "Duration cannot exceed 24 hours (1440 minutes)")
    private double durationMin;

    @Size(max = 500, message = "Notes cannot exceed 500 characters")
    private String notes;

    @PastOrPresent(message = "Timestamp cannot be in the future")
    private Instant timestamp;

    //Constructors
    public StudySessionRequestDTO() {
    }

    public StudySessionRequestDTO(Long subjectId, double durationMin, String notes, Instant timestamp) {
        this.subjectId = subjectId;
        this.durationMin = durationMin;
        this.notes = notes;
        this.timestamp = timestamp;
    }

    //getters and setters
    public Long getSubjectId() {
        return subjectId;
    }
    public void setSubjectId(Long mSubjectId) {
        this.subjectId = mSubjectId;
    }

    public double getDurationMin() {
        return durationMin;
    }
    public void setDurationMin(double mDurationMin) {
        this.durationMin = mDurationMin;
    }

    public String getNotes() {
        return notes;
    }
    public void setNotes(String mNotes) {
        this.notes = mNotes;
    }

    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant mTimestamp) {
        this.timestamp = mTimestamp;
    }
}
