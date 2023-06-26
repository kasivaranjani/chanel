package com.example.chanel.controller;

import com.example.chanel.model.DeliveryChargeDetails;
import com.example.chanel.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeliveryChargeController {
    @Autowired
    ShopService shopService;
    @PostMapping("/saveDeliveryExpenseDetails")
    public ResponseEntity<DeliveryChargeDetails> saveDeliveryExpenseDetails(@RequestBody DeliveryChargeDetails deliveryChargeDetails){
        return  new ResponseEntity<DeliveryChargeDetails>(shopService
                .saveDeliveryAndDetails(deliveryChargeDetails), HttpStatus.ACCEPTED);
    }

   }
