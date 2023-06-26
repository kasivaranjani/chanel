package com.example.chanel.repository;

import com.example.chanel.model.JentleCart;
import com.example.chanel.model.JentleProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<JentleCart,Integer> {

@Query(value="SELECT price FROM JentleCart;",nativeQuery = true)
    List<JentleCart> getAllPriceFromCart();

}