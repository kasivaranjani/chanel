package com.example.chanel.service;

import com.example.chanel.model.*;
import com.example.chanel.userException.UserDefinedException;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


public interface ShopService {
    //1
    public JentleProducts saveJentleProducts(@RequestBody JentleProducts jProducts);
    //2
    public Optional<List<JentleProducts>> getJentleProducts() throws UserDefinedException;
    //3
    public Optional<JentleProducts> getJentleProductsById(Integer product_id) throws UserDefinedException;
    //4
    public List<JentleProducts> getJentleProductsByCategory(String Category) throws UserDefinedException;

    //5
    public JentleCart addProductsToCartById(Integer product_id, String emailId,int quantity);
    //6
    List<JentleCart> getAllProductsFromCart();
    //7
    public Optional<JentleCart> getJCartProductsById(Integer product_id);
    String deleteCartProductsById(Integer product_id);
    //8
    public Optional<List<JentleProducts>> getLimitedJProducts(int limitno);
    //9
    public Optional<List<JentleCart>> getLimitedJCartProducts(int limitno);
    //10
    public List<JentleProducts> filterJProductsByRange(int range,int range2);

    //11
    public default List<JentleProducts> filterJProductsByRangeOfCategory(String category, int range, int range2) {
        return null;
    }

    //12
    public JentleProducts changeJProductsUsingPut(Integer product_id,String availability_region,int availability_stock, float rate) ;
    //13
    public JentleProducts changeJProductsUsingPatch(JentleProducts jProducts);
    //14
    public List<JentleProducts> orderJProductsByRate(String ads);
    //15
    public List<JentleProducts> OrderJProductsByCategory(String ads);
    //16
    public List<JentleProducts> OrderJProductsByRatings(String ads);
    //17
    public List<JentleProducts> OrderJProductsByName(String ads);
    //18
    public List<JentleProducts> OrderJProductsByReviewCount(String ads);
    //19
    public String deleteProductsById(Integer prodId);

    //20

    public PurchaseOrder buyAllProductsFromJCart(String country, String promoCode, String emailId);
    public String getPurchaseConfirmationEmailContent(PurchaseOrder purchaseOrder);
    public CustomerInfo saveCustomerDetails(CustomerInfo customerDetails,String referCode);
    public DeliveryChargeDetails saveDeliveryAndDetails(DeliveryChargeDetails deliveryChargeDetails);

    public List<JentleProducts> OrderJProductsByRatingsAndName(String ads);
    public void sendReferralCode(String recipientEmail,String hostEmail);

    //public void sendEmail(String to, String subject, String body);

   // public void sendPurchaseOrderEmail(PurchaseOrder purchaseOrder, String recipientEmail) throws MessagingException;

    //SmsServiceImpl

}


