package com.example.chanel.repository;

import com.example.chanel.model.CustomerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<CustomerInfo,Integer> {
List<CustomerInfo> findByEmailId(String emailId);
}
