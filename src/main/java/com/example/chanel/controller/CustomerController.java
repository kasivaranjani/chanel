package com.example.chanel.controller;

import com.example.chanel.model.CustomerInfo;
import com.example.chanel.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    @Autowired
    ShopService shopService;
    @PostMapping("/saveCustomerInfo/{referCode}")
    public ResponseEntity<CustomerInfo> saveCustomerInfo(@RequestBody CustomerInfo customerInfo, @PathVariable String referCode){
        return new ResponseEntity<CustomerInfo>(shopService.saveCustomerDetails(customerInfo,referCode), HttpStatus.ACCEPTED);
    }
}
