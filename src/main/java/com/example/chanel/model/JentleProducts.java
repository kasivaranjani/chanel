package com.example.chanel.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table
public class JentleProducts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer product_id;
    @Column(nullable = false)
    @JsonProperty("name")
    String name;
    @Column(nullable = false)
    @JsonProperty("category")
    String category;
    @Column(nullable = false)
    @JsonProperty("price")
    float price;
    @Column(nullable = false)
    @JsonProperty("stock")
    int stock;
    @Column(nullable = false)
    @JsonProperty("availability")
    String  region_availability;
    @Column(nullable = false)
    @JsonProperty("description")
    String  description;
    @Column(nullable = false)
    @JsonProperty("ratings")
    float ratings;
    @Column(nullable = false)
    @JsonProperty("reviewcount")
    int reviewCount;

}
