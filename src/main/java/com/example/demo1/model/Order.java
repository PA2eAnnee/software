package com.example.demo1.model;

import java.time.LocalDateTime;
import java.util.Date;

public class Order {
    private int id;
    private LocalDateTime send_date;
    private int total_price;
    private int id_user;

    public Order(int id, LocalDateTime sendDate, int totalPrice, int userId) {
        this.id = id;
        this.send_date = sendDate;
        this.total_price = totalPrice;
        this.id_user = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getSendDate() {
        return send_date;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.send_date = sendDate;
    }

    public int getTotalPrice() {
        return total_price;
    }

    public void setTotalPrice(int totalPrice) {
        this.total_price = totalPrice;
    }

    public int getUserId() {
        return id_user;
    }

    public void setUserId(int userId) {
        this.id_user = userId;
    }
}

