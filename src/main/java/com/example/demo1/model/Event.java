package com.example.demo1.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Event {
    private int id;
    private String description;
    private int type;
    private int maxMembers;
    private int price;

    LocalDateTime end_date;
    LocalDateTime start_date;

    private int id_site;


    private int recipe_id;

    public Event(int id, String description, int type, int maxMembers, int price, LocalDateTime startDate, LocalDateTime endDate, int siteId) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.maxMembers = maxMembers;
        this.price = price;
        this.start_date = startDate;
        this.end_date = endDate;
        this.id_site = siteId;
    }

    // getters and setters
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public LocalDateTime getStartDate() {
        return start_date;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.start_date = startDate;
    }

    public LocalDateTime getEndDate() {
        return end_date;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.end_date = endDate;
    }

    public int getSiteId() {
        return id_site;
    }

    public void setSiteId(int siteId) {
        this.id_site = siteId;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }
}

