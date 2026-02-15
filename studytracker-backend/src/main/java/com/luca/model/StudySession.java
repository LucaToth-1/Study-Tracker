package com.luca.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;


@Entity
public class StudySession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;
    private Long id;
    private Long subjectId;
    private double durationMin; //we will have the duration in minutes for now
    private String notes;
    private Instant timestamp;

    //no-arg constructor required by JPA
    public StudySession() {
    }
    
    

    //constructor for the study session object
    public StudySession(long Mid, long mSubjectId, double mDurationMin, String mNotes, Instant mTimestamp) {
        this.id = Mid;
        this.subjectId = mSubjectId;
        this.durationMin = mDurationMin;
        this.notes = mNotes;
        this.timestamp = mTimestamp;
    }

    //getters and setters for each attribute
    public Long getId() {
        return id;
    }

    public void setId(Long mID) {
        id = mID;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long mSubjectId) {
        subjectId = mSubjectId;
    }

    public double getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(double mDurationMin) {
        durationMin = mDurationMin;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String mNotes) {
        notes = mNotes;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant mTimestamp) {
        timestamp = mTimestamp;
    }

}
