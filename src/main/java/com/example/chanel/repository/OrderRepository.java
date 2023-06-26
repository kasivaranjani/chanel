package com.example.chanel.repository;

import com.example.chanel.model.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<PurchaseOrder,Long> {
}
