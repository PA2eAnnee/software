package com.example.demo1.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Diplomes {
    private int id;
    private String description;
    private int user_id;
    private LocalDateTime date_obtention;

    public Diplomes() {
    }

    public Diplomes(int id, String description, int user_id, LocalDateTime date_obtention) {
        this.id = id;
        this.description = description;
        this.user_id = user_id;
        this.date_obtention = date_obtention;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public LocalDateTime getDate_obtention() {
        return date_obtention;
    }

    public void setDate_obtention(LocalDateTime date_obtention) {
        this.date_obtention = date_obtention;
    }
}

