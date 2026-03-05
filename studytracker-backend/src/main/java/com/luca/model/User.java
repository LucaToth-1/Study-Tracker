package com.luca.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class User{
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    //Private attributes
    private String username;
    //This needs to be hashed later, commenting out passwork related functions
    // private String password;

    //Constructor
    //empty for JPA
    public User(){
    }

    //Getters and Setters
    public void setId(Long mId){
        this.id = mId;
    } 

    public void setUsername(String username){
        this.username = username;
    } 

    // public void setPassword(String password){
    //     this.password = password;
    // }

     public Long getId(){
        return id;
    }
    
    public String getUsername(){
        return username;
    }

    // public String getPassword(){
    //      return password;
    // }
}
