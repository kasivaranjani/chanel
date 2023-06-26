package com.example.chanel.repository;

import com.example.chanel.model.JentleProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository  extends JpaRepository<JentleProducts,Integer> {
    List<JentleProducts> findByCategory(String Category);

}
