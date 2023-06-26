package com.example.chanel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class CustomerInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer customerId;
    @Column(nullable = false)
    @JsonProperty("customername")
    String customerName;
    @Column(nullable = false)
    @JsonProperty("phone")
    String phoneNumber;
    @Column(nullable = false)
    @JsonProperty("address")
    String address;
    @Column(nullable = false)
    @JsonProperty("email")
    String emailId;
    @Column(nullable = false)
    @JsonProperty("referralcode")
    String referralCode;
    @Column(nullable = false)
    @JsonProperty("referralcount")
    int referralCount;


}
