package com.example.demo1.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Ticket {
    private int id;
    private String title;
    private String description;
    private LocalDateTime creation_date;
    private int id_sender;

    public Ticket(int id, String title, String description, LocalDateTime creationDate, int senderId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creation_date = creationDate;
        this.id_sender = senderId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creation_date;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creation_date = creationDate;
    }

    public int getSenderId() {
        return id_sender;
    }

    public void setSenderId(int senderId) {
        this.id_sender = senderId;
    }
}

