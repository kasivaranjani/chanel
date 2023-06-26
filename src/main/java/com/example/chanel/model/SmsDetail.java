package com.example.chanel.model;

import lombok.Data;

@Data
public class SmsDetail {
    private String to;
    private String body;
    public SmsDetail() {
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
}

