package com.example.chanel.controller;

import com.example.chanel.model.JentleProducts;
import com.example.chanel.service.ShopService;
import com.example.chanel.userException.UserDefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
public class ShopController {
    @Autowired
    ShopService shopService;

    @PostMapping("/saveProducts")//1      url: http://localhost:8080/saveProducts
    public ResponseEntity<JentleProducts> savedJProducts(@RequestBody JentleProducts savingProducts) {
        return new ResponseEntity<JentleProducts>(shopService.saveJentleProducts(savingProducts), HttpStatus.ACCEPTED);
    }

   //@GetMapping("/getAllProductAndDetails")  url:http://localhost:8080/getAllProductAndDetails
    @GetMapping(value= "/getAllProductAndDetails",produces = "application/json")//2
    public ResponseEntity<List<JentleProducts>> showJProducts() throws UserDefinedException {
        List<JentleProducts> JProductDetails = shopService.getJentleProducts().get();
        return new ResponseEntity<>(JProductDetails, HttpStatus.ACCEPTED);
    }
    @GetMapping("/getProductById/{Id}")//3   url:http://localhost:8080/getProductById/15
    public ResponseEntity<Optional<JentleProducts>> showJProductsById(@PathVariable("Id") Integer product_id) throws UserDefinedException {
        return new ResponseEntity<Optional<JentleProducts>>(shopService.getJentleProductsById(product_id), HttpStatus.ACCEPTED);
    }
    @GetMapping("/getProductsByCategory/{Category}")//4  url:http://localhost:8080/getProductsByCategory/Electronics
    public ResponseEntity<List<JentleProducts>> showJProductsByCategory(@PathVariable("Category") String category) {
        return new ResponseEntity<List<JentleProducts>>(shopService.getJentleProductsByCategory(category), HttpStatus.ACCEPTED);
    }
    @GetMapping("/getLimitedProducts/{limitno}")//8
    public ResponseEntity<Optional<List<JentleProducts>>> showLimitedJProducts(@PathVariable int limitno){
        return new ResponseEntity<Optional<List<JentleProducts>>>(shopService.getLimitedJProducts(limitno),HttpStatus.ACCEPTED);
    }

    @GetMapping("/getLimitedProductsByRate/{range}/{range2}")//10
    public ResponseEntity<List<JentleProducts>> showLimitedJProductsByRange(@PathVariable int range,@PathVariable int range2){
        return new ResponseEntity<List<JentleProducts>>(shopService.filterJProductsByRange(range,range2),HttpStatus.ACCEPTED);
    }
    //http://localhost:8080/getRangeBasedJProductsByCategory?category=shirts&&range=500&&range2=1000
    @GetMapping("/getRangeBasedJProductsByCategory")//11

    public ResponseEntity<List<JentleProducts>> showJProductsBasedOnRangeOfCategory(@RequestParam String category,
                                                                                    @RequestParam  int range,
                                                                                    @RequestParam  int range2){
        return new ResponseEntity<List<JentleProducts>>(shopService.filterJProductsByRangeOfCategory(category,range,range2),HttpStatus.ACCEPTED);
    }
    @PutMapping("/updateJProductDetailsUsingPut/{product_id}/{availability_region}/{availability_stock}/{rate}")//12
    public ResponseEntity<JentleProducts> updateJProductsUsingPut(@PathVariable Integer product_id,@PathVariable String availability_region,@PathVariable int availability_stock,@PathVariable float rate){
        return new ResponseEntity<JentleProducts>(shopService.changeJProductsUsingPut(product_id,availability_region,availability_stock,rate),HttpStatus.ACCEPTED);
    }
    @PatchMapping("/updateJProductDetails")//13
    public ResponseEntity<JentleProducts> updateJProductsUsingPatch(@RequestBody JentleProducts jProducts){
        return new ResponseEntity<JentleProducts>(shopService.changeJProductsUsingPatch(jProducts),HttpStatus.ACCEPTED);
    }
    @GetMapping("/OrderJProductByRate")//14
    public ResponseEntity<List<JentleProducts>> arrangeProductsByRate(@RequestParam String ads) {

        return new ResponseEntity<List<JentleProducts>>(shopService.orderJProductsByRate(ads), HttpStatus.ACCEPTED);

    }
    @GetMapping("/OrderJProductByCategory")//15
    public ResponseEntity<List<JentleProducts>> arrangeProductsByCategory(@RequestParam String ads) {

        return new ResponseEntity<List<JentleProducts>>(shopService.OrderJProductsByCategory(ads), HttpStatus.ACCEPTED);

    }
    @GetMapping("/OrderJProductByRatings")//16
    public ResponseEntity<List<JentleProducts>> arrangeProductsByRatings(@RequestParam String ads) {
        return new ResponseEntity<List<JentleProducts>>(shopService.OrderJProductsByRatings(ads), HttpStatus.ACCEPTED);
    }
    @GetMapping("/OrderJProductByName")//17
    public ResponseEntity<List<JentleProducts>> arrangeProductsByName(@RequestParam String ads) {
        return new ResponseEntity<List<JentleProducts>>(shopService.OrderJProductsByName(ads), HttpStatus.ACCEPTED);
    }
    @GetMapping("/OrderJProductByReviewCount")//18
    public ResponseEntity<List<JentleProducts>> arrangeProductsByReviewCount(@RequestParam String ads) {
        return new ResponseEntity<List<JentleProducts>>(shopService.OrderJProductsByReviewCount(ads), HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/deleteProductsById/{Id}")//19
    public ResponseEntity<String> DeleteJProductById(@PathVariable Integer Id){
        return new ResponseEntity<String>(shopService.deleteProductsById(Id),HttpStatus.OK);
    }
    @GetMapping("/OrderJProductByRatingsAndName")//21
    public ResponseEntity<List<JentleProducts>> arrangeProductsByRatingsandName(@RequestParam String ads) {
        return new ResponseEntity<List<JentleProducts>>(shopService.OrderJProductsByRatingsAndName(ads), HttpStatus.ACCEPTED);
    }

}


