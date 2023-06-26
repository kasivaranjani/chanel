package com.example.chanel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;
@Table
@Data
@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     Long id;
    @Column(nullable=false)
    @JsonProperty("customername")
     String customerName;
    @Column(nullable=false)
    @JsonProperty("orderdate")
     LocalDate orderDate;
    @Column(nullable=false)
    @JsonProperty("totalprice")
     double totalPrice;
    @Column(nullable=false)
    @JsonProperty("emailid")
    String emailId;



}