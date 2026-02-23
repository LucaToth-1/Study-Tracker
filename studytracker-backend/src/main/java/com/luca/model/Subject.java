package com.luca.model;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double totalStudyTimeMin = 0.0;
    //this will just keep track of thwne the subject was created
    @CreationTimestamp
    private Instant createdAt = Instant.now();

    @UpdateTimestamp
    private Instant updatedAt = Instant.now();

    // Bidirectional relationship: a Subject can have many StudySessions.
    // 'mappedBy = "subject"' tells Hibernate that the foreign key is managed by
    // the 'subject' field in StudySession, so no duplicate column is created.
    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<StudySession> sessions;

    //no-arg constructor required by JPA
    public Subject() {
    }

    //constructor that will create a subject object
    //each subject has it's own ID, name, and description
    public Subject(Long mId, String mName, Instant mCreatedAt, Instant mUpdatedAt, double mTotalStudyTimeMin) {
        this.id = mId;
        this.name = mName;
        this.createdAt = mCreatedAt;
        this.updatedAt = mUpdatedAt;
        this.totalStudyTimeMin = mTotalStudyTimeMin;
    }

    //a bunch of getters and setters for each attribute
    // I don't know if we will need all of them but just in case good for OOP
    public Long getId(){
        return id;
    }

    public void setId(Long mID){
        id = mID;
    }

    public String getName(){
        return name;
    }

    public void setName(String mName){
        name = mName;
    }

    //one final getter for the created at timestamp
    public Instant getCreatedAt(){
        return createdAt;
    }

    public Instant getUpdatedAt(){
        return updatedAt;
    }
    public void setUpdatedAt(Instant mUpdatedAt){
        updatedAt = mUpdatedAt;
    }

    //getter and setter for study sessions
    public List<StudySession> getSessions(){
        return sessions;
    }

    public void setSessions(List<StudySession> mSessions){
        sessions = mSessions;
    }

    public double getTotalStudyTimeMin() {
        return totalStudyTimeMin;
    }
    public void setTotalStudyTimeMin(double mTotalStudyTimeMin) {
        this.totalStudyTimeMin = mTotalStudyTimeMin;
    }

}
