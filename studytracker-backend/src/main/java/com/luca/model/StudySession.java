package com.luca.model;

import java.time.Instant;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class StudySession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    @JsonBackReference
    private Subject subject;

    private double durationMin;
    
    @Column(length =1000)
    private String notes;
    
    private Instant timestamp;

    // Required by JPA
    public StudySession() {}

    public StudySession(Subject subject, double durationMin, String notes, Instant timestamp) {
        this.subject = subject;
        this.durationMin = durationMin;
        this.notes = notes;
        this.timestamp = timestamp;
    }

    // Getters & Setters

    public Long getId() {
        return id;
    }

    public void setId(long ID){
        this.id = ID;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public double getDurationMin() {
        return durationMin;
    }

    public void setDurationMin(double durationMin) {
        this.durationMin = durationMin;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
