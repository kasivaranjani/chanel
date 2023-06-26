package com.example.chanel.controller;

import com.example.chanel.model.JentleCart;
import com.example.chanel.model.PurchaseOrder;
import com.example.chanel.service.ShopService;
import com.example.chanel.userException.UserDefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class CartController {
    @Autowired
    ShopService shopService;
    @GetMapping("/getProductsFromCart")//6    url:http://localhost:8080/getProductsFromCart
    public ResponseEntity<List<JentleCart>> showJCartProducts(){
        return new ResponseEntity<List<JentleCart>>(shopService.getAllProductsFromCart(),HttpStatus.ACCEPTED);
    }

    @PostMapping("/SaveFromJProductsToCartById/{product_id}/{emailId}/{quantity}")//5  url:http://localhost:8080/SaveFromJProductsToCartById/23/kasivaranjani2001%40gmail.com/2
    public ResponseEntity<JentleCart> saveJProductsIntoCartUsingId(@PathVariable Integer product_id, @PathVariable String emailId,@PathVariable int quantity){
        return new ResponseEntity<JentleCart>(shopService.addProductsToCartById(product_id,emailId,quantity),HttpStatus.ACCEPTED);
    }
    @GetMapping("/getLimitedJCartProducts/{limitno}")//9
    public ResponseEntity<Optional<List<JentleCart>>> showLimitedCartProducts(@PathVariable int limitno){
        return new ResponseEntity<Optional<List<JentleCart>>>(shopService.getLimitedJCartProducts(limitno),HttpStatus.ACCEPTED);
    }

    @GetMapping("/getJCartProductById/{Id}")//7
    public ResponseEntity<Optional<JentleCart>> showJCartProductsById(@PathVariable("Id") Integer product_id) throws UserDefinedException {
        return new ResponseEntity<Optional<JentleCart>>(shopService.getJCartProductsById(product_id), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deleteCartProductsById/{pId}")
    public ResponseEntity<String> DeleteCartProductById(@PathVariable Integer pId){
        return new ResponseEntity<String>(shopService.deleteCartProductsById(pId),HttpStatus.OK);
    }

    @PostMapping("/deleteCartProductsByCustomerIdAndProductId/{country}/{promocode}/{emailId}")
    public ResponseEntity<PurchaseOrder> deleteCartProductsByCustomerIdAndProductId(@PathVariable String country, @PathVariable String promocode, String emailId){
        return new ResponseEntity<PurchaseOrder>(shopService.buyAllProductsFromJCart(country,promocode,emailId),HttpStatus.ACCEPTED);
    }
}
