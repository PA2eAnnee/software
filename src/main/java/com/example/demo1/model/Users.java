package com.example.demo1.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Users {
    private int id;
    private String name;
    private String first_name;
    private String password;
    private String username;
    private String email;
    private String role;
    private String subscription;
    private String picture;
    private LocalDateTime creation_time;

    // Constructor
    public Users(int id, String name, String firstName, String password, String username, String email, String role, String subscription, String picture, LocalDateTime creationTime) {
        this.id = id;
        this.name = name;
        this.first_name = firstName;
        this.password = password;
        this.username = username;
        this.email = email;
        this.role = role;
        this.subscription = subscription;
        this.picture = picture;
        this.creation_time = creationTime;
    }


    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return first_name;
    }

    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSubscription() {
        return subscription;
    }

    public void setSubscription(String subscription) {
        this.subscription = subscription;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public LocalDateTime getCreationTime() {
        return creation_time;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creation_time = creationTime;
    }
}
