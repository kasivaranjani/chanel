package com.example.chanel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table
public class DeliveryChargeDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer countryId;
    @Column(nullable = false)
    @JsonProperty("countryname")
    String countryName;
    @Column(nullable = false)
    @JsonProperty("deliverycharge")
    double deliveryExpense;
}
