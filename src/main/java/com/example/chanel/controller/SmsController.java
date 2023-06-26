package com.example.chanel.controller;


import com.example.chanel.model.SmsDetail;
import com.example.chanel.service.ShopService;
import com.example.chanel.service.SmsInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class SmsController {
    @Autowired
    SmsInterface smsInterface;

    @PostMapping("/send-sms")
    public String sendSms(@RequestBody SmsDetail smsRequest) {
        return smsInterface.sendSms(smsRequest.getTo(), smsRequest.getBody());
    }

}
