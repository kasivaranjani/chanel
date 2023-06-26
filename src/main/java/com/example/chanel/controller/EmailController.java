package com.example.chanel.controller;

import com.example.chanel.model.EmailRequest;
import com.example.chanel.model.JentleProducts;
import com.example.chanel.model.PurchaseOrder;
import com.example.chanel.service.ShopService;
import com.example.chanel.userException.UserDefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;

@RestController
public class EmailController {
    @Autowired
    ShopService shopService;
   /* @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        // Extract the necessary details from the request
        String to = emailRequest.getTo();
        String subject = emailRequest.getSubject();
        String body = emailRequest.getBody();

        // Send the email
        shopService.sendEmail(to, subject, body);

        return "Email sent successfully";
    }*/
  /*  @PostMapping("/purchase-orders")
    public void createPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        // Save the purchase order to the database

        // Send the purchase order email
        try {
            shopService.sendPurchaseOrderEmail(purchaseOrder, purchaseOrder.getEmailId());
        } catch (MessagingException e) {
            throw new UserDefinedException("MailIdNotFound");
        }
    }*/
   @PostMapping("/invite/{recipienteMail}/{hostMail}")
   public ResponseEntity<String> sendInvitationEmail(@PathVariable String recipienteMail, @PathVariable String hostMail) {
       // Validate the email address and perform any necessary checks

       // Call the email service to send the invitation email
       shopService.sendReferralCode(recipienteMail,hostMail);

       return ResponseEntity.ok("Invitation email sent successfully");
   }

}
