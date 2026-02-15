package com.luca.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class SubjectRequestDTO {

    @NotBlank(message = "Subject name cannot be blank")
    @Size(min = 2, max = 50, message = "Subject name must be between 2 and 50 characters")
    private String name;

    public String getName() { 
        return name; 
    }
    public void setName(String name) { 
        this.name = name; 
    }
}