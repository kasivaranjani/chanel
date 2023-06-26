package com.example.chanel.controller;

import com.example.chanel.model.WhatsappRequest;
import com.example.chanel.service.WhatsappServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WhatsappController {

    private final WhatsappServiceInterface whatsappService;

    @Autowired
    public WhatsappController(WhatsappServiceInterface whatsappService) {
        this.whatsappService = whatsappService;
    }
    @PostMapping("/send-whatsapp")
    public void sendWhatsApp(@RequestBody WhatsappRequest request) {
        String toPhoneNumber = request.getToPhoneNumber();
        String message = request.getMessage();

        whatsappService.sendWhatsAppMessage(toPhoneNumber, message);
    }
}
