package com.example.chanel.service.impl;

import com.example.chanel.service.WhatsappServiceInterface;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class WhatsappImpl implements WhatsappServiceInterface {

    @Value("${twilio.accountSid}")
    private String accountSid;

    @Value("${twilio.authToken}")
    private String authToken;

    @Value("${twilio.phoneNumber}")
    private String twilioPhoneNumber;

    public String sendWhatsAppMessage(String toPhoneNumber, String message) {
        Twilio.init(accountSid, authToken);

        Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:" + toPhoneNumber),
                        new com.twilio.type.PhoneNumber("whatsapp:" + twilioPhoneNumber),
                        message)
                .create();
        return "Whatsapp message sent Successfully";
    }
}