package com.luca.dto;

import java.time.Instant;

public class SubjectResponseDTO {
    //Attributes
    private Long id;
    private String name;
    private Instant createdAt;
    private double totalStudyTimeMin;
    
    //Constructors
    public SubjectResponseDTO() {
    }
    
    public SubjectResponseDTO(Long mId, String mName, Instant mCreatedAt, double mTotalStudyTimeMin) {
        this.id = mId;
        this.name = mName;
        this.createdAt = mCreatedAt;
        this.totalStudyTimeMin = mTotalStudyTimeMin;
    }
    
    //Getter Functions
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }

    //Setter Functions
    public void setId(Long mId){
        this.id = mId;
    }
    public void setName(String mName){
        this.name = mName;
    }
    public void setCreatedAt(Instant mCreatedAt){
        this.createdAt = mCreatedAt;
    }

    public double getTotalStudyTimeMin() {
        return totalStudyTimeMin;
    }
    public void setTotalStudyTimeMin(double mTotalStudyTimeMin) {
        this.totalStudyTimeMin = mTotalStudyTimeMin;
    }
    
}
