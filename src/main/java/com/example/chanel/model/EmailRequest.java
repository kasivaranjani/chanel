package com.example.chanel.model;

import lombok.Data;

@Data
public class EmailRequest {
    private String to;
    private String subject;
    private String body;

    // Add constructors, getters, and setters
}