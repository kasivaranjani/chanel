package com.example.chanel.repository;

import com.example.chanel.model.DeliveryChargeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryExpenseRepository extends JpaRepository<DeliveryChargeDetails,Integer> {
}
