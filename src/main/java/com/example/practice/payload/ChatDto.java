package com.example.practice.payload;

import com.example.practice.modal.Status;

public class ChatDto {
    private Long id;
    private Long sender;
    private Long receiver;
    private String message;
    private String date;
    private Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender_id) {
        this.sender = sender_id;
    }

    public Long getReceiver() {
        return receiver;
    }

    public void setReceiver(Long receiver_id) {
        this.receiver = receiver_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return sender + " " + receiver + " " + message + " " + date;
    }
}
