package com.example.chanel.service.impl;

import com.example.chanel.model.*;
import com.example.chanel.repository.*;
import com.example.chanel.service.ShopService;
import com.example.chanel.userException.UserDefinedException;
import com.example.chanel.userException.UserIllegalArgumentException;
import com.example.chanel.userException.UserRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import org.thymeleaf.context.Context;

@Service
public class ShopServiceImpl extends UserDefinedException implements ShopService {
    @Autowired
    ShopRepository shopRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
     JavaMailSender mailSender;
    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DeliveryExpenseRepository deliveryExpenseRepository;


    @Override//1  ok
    public JentleProducts saveJentleProducts(JentleProducts jProducts) {
        Optional<List<JentleProducts>> jProductListOptional= Optional.of(shopRepository.findAll());

        if (jProductListOptional.isPresent()) {
            List<JentleProducts> jProductList = jProductListOptional.get();
            for (JentleProducts jProduct : jProductList) {
                if (jProduct.getName().equals(jProducts.getName()) &&
                        jProduct.getCategory().equals(jProducts.getCategory()) &&
                        jProduct.getPrice() == jProducts.getPrice() &&
                        jProduct.getStock() == jProducts.getStock() &&
                        jProduct.getRegion_availability().equals(jProducts.getRegion_availability()) &&
                        jProduct.getDescription().equals(jProducts.getDescription()) &&
                        jProduct.getRatings() == jProducts.getRatings() &&
                        jProduct.getReviewCount() == jProducts.getReviewCount()) {
                    throw new UserRuntimeException.UserCreatedException("jProducts is already present in jProductList");
                }
            }
        }

        return shopRepository.save(jProducts);
    }

    @Override//2   ok
    public Optional<List<JentleProducts>> getJentleProducts() {

        Optional<List<JentleProducts>> jProductList = Optional.of(shopRepository.findAll());
        if(shopRepository.count() == 0){
            throw new UserDefinedException("Empty Or Null Database Exception");
        }
        else {
            return Optional.of(jProductList.get());
        }
    }

    @Override//3  ok
    public Optional<JentleProducts> getJentleProductsById(Integer product_id) {
        Optional<JentleProducts> jProducts = shopRepository.findById(product_id);

        if (shopRepository.count() == 0) {
            throw new UserDefinedException("Table Is Empty");
        }
        else if (jProducts.isPresent()) {
            return jProducts;
        }
        else {
            throw new UserDefinedException("Id Not Found");
        }
    }
    @Override//4  ok
    public List<JentleProducts> getJentleProductsByCategory(String category) {
        Optional<List<JentleProducts>> productListOptional = Optional.ofNullable(shopRepository.findByCategory(category));
       List<JentleProducts> productList=productListOptional.get();
        if(productListOptional.get().isEmpty() || productList==null) {
            throw new UserDefinedException("Category Not Found");
        }
        return productList;
    }


    /*@Override//5
    public JentleCart addProductsToCartById(Integer product_id, String emailId) {
        Optional<JentleProducts> jentleProductsOptional = Optional.ofNullable(shopRepository.findById(product_id).get());
        Optional<List<CustomerInfo>> customerInfoOptional = Optional.of(customerRepository.findByEmailId(emailId));

            if (jentleProductsOptional.isPresent()) {

                JentleCart jCart = new JentleCart();
                JentleProducts jentleProducts = jentleProductsOptional.get();
                CustomerInfo customerInfo = (CustomerInfo) customerInfoOptional.get();
                jCart.setProduct_id(jentleProducts.getProduct_id());
                jCart.setName(jentleProducts.getName());
                jCart.setCustomer_Name(customerInfo.getCustomerName());
                jCart.setPhoneNo((customerInfo.getPhoneNumber());
                jCart.setAddress(customerInfo.getAddress());
                jCart.setEmailId((customerInfo.getEmailId());
                jCart.setCategory(jentleProducts.getCategory());
                jCart.setPrice(jentleProducts.getPrice());
                jCart.setStock(jentleProducts.getStock());
                jCart.setRegion_availability(jentleProducts.getRegion_availability());
                jCart.setDescription(jentleProducts.getDescription());
                jCart.setRatings(jentleProducts.getRatings());
                jCart.setReviewCount(jentleProducts.getReviewCount());
                return cartRepository.save(jCart);
            }

    }
        else if(shopRepository.count() == 0){
            throw new UserDefinedException("Table Is Empty");
        }
        else {
            throw new UserDefinedException("Id Not Found");
        }


      /*  JentleProducts jentleProducts = shopRepository.findById(product_id)
                .orElseThrow(() -> new UserDefinedException("Product not found with ID: " + product_id));

        JentleCart cart = new JentleCart();
        cart.getProducts().add(jentleProducts);

        return cartRepository.save(cart);
    }*/


/*@Override
public JentleCart addProductsToCartById(Integer product_id, String emailId) {
    Optional<JentleProducts> jentleProductsOptional = Optional.ofNullable(shopRepository.findById(product_id).get());
    Optional<CustomerInfo> customerInfoOptional = customerRepository.findByEmailId(emailId);

        if (!jentleProductsOptional.isPresent()) {
        throw new UserDefinedException("Id Not Found");
        }
        else if (!customerInfoOptional.isPresent()) {
        throw new UserDefinedException("Customer not found");
        }else if(shopRepository.count()==0){
            throw new UserDefinedException("Database Is Empty");
        }

        JentleProducts jentleProducts = jentleProductsOptional.get();
        CustomerInfo customerInfo =  customerInfoOptional.get();

        JentleCart jCart = new JentleCart();
        jCart.setProduct_id(jentleProducts.getProduct_id());
        jCart.setName(jentleProducts.getName());
        jCart.setCustomer_Name(customerInfo.getCustomerName());
        jCart.setPhoneNo(customerInfo.getPhoneNumber());
        jCart.setAddress(customerInfo.getAddress());
        jCart.setEmailId(customerInfo.getEmailId());
        jCart.setCategory(jentleProducts.getCategory());
        jCart.setPrice(jentleProducts.getPrice());
        jCart.setStock(jentleProducts.getStock());
        jCart.setRegion_availability(jentleProducts.getRegion_availability());
        jCart.setDescription(jentleProducts.getDescription());
        jCart.setRatings(jentleProducts.getRatings());
        jCart.setReviewCount(jentleProducts.getReviewCount());

        return cartRepository.save(jCart);
        }*/

    @Override//5  ok
    public JentleCart addProductsToCartById(Integer product_id, String emailId,int quantity) {
        Optional<JentleProducts> jentleProductsOptional = shopRepository.findById(product_id);
        Optional<CustomerInfo> customerInfoOptional = customerRepository.findByEmailId(emailId)
                .stream().findFirst();

        if (!jentleProductsOptional.isPresent()) {
            throw new UserDefinedException("Product ID not found");
        }

        if (!customerInfoOptional.isPresent()) {
            throw new UserDefinedException("Customer not found");
        }
        else if(shopRepository.count()==0){
            throw new UserDefinedException("No Item In Database");
        }
        else if(customerRepository.count()==0){
            throw new UserDefinedException("No customer Data Found");
        }
        JentleProducts jentleProducts=jentleProductsOptional.get();
        JentleProducts jentleProductsno = jentleProductsOptional.get();
        CustomerInfo customerInfo = customerInfoOptional.get();

        JentleCart jCart = new JentleCart();
        jCart.setProduct_id(jentleProducts.getProduct_id());
        jCart.setName(jentleProducts.getName());
        jCart.setCustomer_Name(customerInfo.getCustomerName());
        jCart.setPhoneNo(customerInfo.getPhoneNumber());
        jCart.setAddress(customerInfo.getAddress());
        jCart.setEmailId(customerInfo.getEmailId());
        jCart.setCategory(jentleProducts.getCategory());
        jCart.setPrice(jentleProducts.getPrice());
        jCart.setStock(jentleProducts.getStock());
        if(jentleProductsno.getStock()>quantity) {
            jCart.setQuantity(quantity);
        }
        else{
            throw new UserDefinedException("stock is lower than orderingQuantity");
        }
        jCart.setRegion_availability(jentleProducts.getRegion_availability());
        jCart.setDescription(jentleProducts.getDescription());
        jCart.setRatings(jentleProducts.getRatings());
        jCart.setReviewCount(jentleProducts.getReviewCount());

        return cartRepository.save(jCart);
    }
    @Override//6 ok
    public List<JentleCart> getAllProductsFromCart() {
        Optional<List<JentleCart>> JCartProducts= Optional.of(cartRepository.findAll());
        if (JCartProducts.get().isEmpty()) {
            throw new UserDefinedException("Product Not Found");
        }
        else
            return JCartProducts.get();
    }

    @Override//7 ok
    public Optional<JentleCart> getJCartProductsById(Integer product_id) {
        Optional<JentleCart> jProducts = cartRepository.findById(product_id);

        if (shopRepository.count() == 0) {
            throw new UserDefinedException("Table Is Empty");
        }
        else if (jProducts.isPresent()) {
            return jProducts;
        }
        else {
            throw new UserDefinedException("Id Not Found");
        }
    }
    @Override
    public String deleteCartProductsById(Integer product_id) {
        if (cartRepository.existsById(product_id)) {
            cartRepository.deleteById(product_id);
            return "Deleted Successfully";
        }
        else if(shopRepository.count() == 0){
            throw new UserDefinedException("Table Is Empty");
        }
        else
            throw new UserDefinedException("This ID is not available");
    }


    @Override//8
    public Optional<List<JentleProducts>> getLimitedJProducts(int limitno) {
        // Stream<JentleProducts>
        Optional<List<JentleProducts>> allJProducts = Optional.of(shopRepository.findAll());
        if (allJProducts.get().isEmpty()) {
            throw new UserDefinedException("Product Not Found");
        } else if(shopRepository.count() == 0){
            throw new UserDefinedException("Table Is Empty");
        }
        else{
            List<JentleProducts> limitJProducts =  allJProducts.orElse(Collections.emptyList()).stream().limit(limitno).collect(Collectors.toList());
            return Optional.of(limitJProducts);
        }
    }

    @Override//9
    public Optional<List<JentleCart>> getLimitedJCartProducts(int limitno) {
        // Stream<JentleProducts>
        Optional<List<JentleCart>> allJProducts = Optional.of(cartRepository.findAll());
        if (allJProducts.get().isEmpty()) {
            throw new UserDefinedException("Product Not Found");
        } else if(shopRepository.count() == 0){
            throw new UserDefinedException("Table Is Empty");
        }
        else{
            List<JentleCart> limitJProducts =  allJProducts.orElse(Collections.emptyList()).stream().limit(limitno).collect(Collectors.toList());
            return Optional.of(limitJProducts);
        }
    }
    @Override//10
    public List<JentleProducts> filterJProductsByRange(int range, int range2) {
        Optional<List<JentleProducts>> JProductlist = Optional.of(shopRepository.findAll());
        List<JentleProducts> productList=JProductlist.get();
        if (productList.isEmpty() || productList==null) {
            throw new UserDefinedException("Product Not Found");
        }
        else if(shopRepository.count() == 0){
            throw new UserDefinedException("Table Is Empty");
        }
        else {
            List<JentleProducts> filteredList = JProductlist.orElse(Collections.emptyList()).stream().filter(i -> i.getPrice() >= range && i.getPrice() <= range2).collect(Collectors.toList());
            if (filteredList.isEmpty() || filteredList==null) {
                throw new UserDefinedException("Product Not Found");
            }
            return filteredList;
        }
    }

    @Override//11
    public List<JentleProducts> filterJProductsByRangeOfCategory(String category, int range, int range2) {
        Optional<List<JentleProducts>> filteredList = Optional.ofNullable(shopRepository.findByCategory(category));

        if (filteredList.isPresent()) {
            List<JentleProducts> rangeList = filteredList.get().stream()
                    .filter(i -> i.getPrice() >= range && i.getPrice() <= range2)
                    .collect(Collectors.toList());

            if (rangeList.isEmpty()) {
                throw new UserDefinedException("No Item Found within the Specified Range");
            }

            return rangeList;
        } else {
            throw new UserDefinedException("No Item Found in Database");
        }
    }

    @Override//12
    public JentleProducts changeJProductsUsingPut(Integer product_id, String availability_region, int availability_stock, float rate) {
        Optional<JentleProducts> jentleProducts = shopRepository.findById(product_id);

        if (!jentleProducts.isPresent()) {
            throw new UserDefinedException("Id Not Found");
        } else if (shopRepository.count() == 0) {
            throw new UserDefinedException("No Item In Database");
        }
        JentleProducts jProduct = jentleProducts.get();
        jProduct.setPrice(rate);
        jProduct.setRegion_availability(availability_region);
        jProduct.setStock(jProduct.getStock() + availability_stock);
        return shopRepository.save(jProduct);
    }
    @Override//13
    public JentleProducts changeJProductsUsingPatch(JentleProducts jProducts) {
        Optional<JentleProducts> jentleProducts = shopRepository.findById(jProducts.getProduct_id());

        if (!jentleProducts.isPresent()) {
            throw new UserDefinedException("Id Not Found");
        } else if(shopRepository.count() == 0){
            throw new UserDefinedException("No Item In Database");
        }
        JentleProducts existingProduct = jentleProducts.get();
        existingProduct.setPrice(jProducts.getPrice());
        existingProduct.setRegion_availability(jProducts.getRegion_availability());
        existingProduct.setStock(existingProduct.getStock() + jProducts.getStock());
        return shopRepository.save(existingProduct);
    }

    /* @Override
     public List<JentleProducts> AscJProductsByRate() {
         Optional<List<JentleProducts>> jCart = Optional.of(shopRepository.findAll());
         if (!jCart.isPresent()) {
             throw new UserDefinedException("No Items Found");
         } else if (shopRepository.count() == 0) {
             throw new UserDefinedException("No Items in Database");
         }

         List<JentleProducts> jCartRate = jCart.get();
         List<JentleProducts> jCartSortRate = jCartRate.stream()
                 .sorted(Comparator.comparing(JentleProducts::getRate))
                 .collect(Collectors.toList());

         return jCartSortRate;
     }*/
    @Override//14
    public List<JentleProducts> orderJProductsByRate(String ads) {
        Optional<List<JentleProducts>> jCart = Optional.of(shopRepository.findAll());
        List<JentleProducts> jCartRate = jCart.get();
        List<JentleProducts> jCartSortRate;
        if (shopRepository.count() == 0) {
            throw new UserDefinedException("No Items in Database");
        }
        else if (ads.equalsIgnoreCase("asc")) {
            jCartSortRate = jCartRate.stream()
                    .sorted(Comparator.comparing(JentleProducts::getPrice))
                    .collect(Collectors.toList());
        } else if (ads.equalsIgnoreCase("desc")) {
            jCartSortRate = jCartRate.stream()
                    .sorted(Comparator.comparing(JentleProducts::getPrice).reversed())
                    .collect(Collectors.toList());
        } else {
            throw new UserIllegalArgumentException("Invalid sorting order. Valid values are 'asc' or 'desc'.");
        }
        return jCartSortRate;
    }

    @Override//15
    public List<JentleProducts> OrderJProductsByCategory(String ads) {
        // List<JentleProducts> JCartRate=shopRepository.findAll(Sort.by(Sort.Direction.ASC,"category"));
        Optional<List<JentleProducts>> JCart= Optional.of(shopRepository.findAll());
        if (!JCart.isPresent()) {
            throw new UserDefinedException("Id Not Found");
        } else if(shopRepository.count() == 0){
            throw new UserDefinedException("No Item In Database");
        }
        List<JentleProducts> jCartCategory = JCart.get();
        List<JentleProducts> JCartSortCategory;
        if (ads.equalsIgnoreCase("asc")) {
            JCartSortCategory = jCartCategory.stream()
                    .sorted(Comparator.comparing(JentleProducts::getCategory)).collect(Collectors.toList());
        } else if (ads.equalsIgnoreCase("desc")) {
            JCartSortCategory=jCartCategory.stream()
                    .sorted(Comparator.comparing(JentleProducts::getCategory).reversed())
                    .collect(Collectors.toList());
        }
        else{
            throw new UserIllegalArgumentException("Invalid sorting order. Valid values are 'asc' or 'desc'.");
        }
        return JCartSortCategory;
    }
    @Override//16
    public List<JentleProducts> OrderJProductsByRatings(String ads) {
        //   List<JentleProducts> JCartRate=shopRepository.findAll(Sort.by(Sort.Direction.ASC,"ratings"));
        Optional<List<JentleProducts>> JCart= Optional.of(shopRepository.findAll());
        List<JentleProducts> jCartRatings = JCart.get();
        List<JentleProducts> JCartSortRatings;
        if(shopRepository.count() == 0){
            throw new UserDefinedException("No Item In Database");
        } else if(ads.equalsIgnoreCase("asc")) {
            JCartSortRatings = jCartRatings.stream().sorted(Comparator.comparing(JentleProducts::getRatings)).collect(Collectors.toList());
        }
        else if(ads.equalsIgnoreCase("desc")){
            JCartSortRatings = jCartRatings.stream().sorted(Comparator.comparing(JentleProducts::getRatings).reversed()).collect(Collectors.toList());

        }
        else {
            throw new UserIllegalArgumentException("Invalid sorting order. Valid values are 'asc' or 'desc'.");
        }
        return JCartSortRatings;
    }

    @Override//17
    public List<JentleProducts> OrderJProductsByName(String ads) {
        //  List<JentleProducts> JCartRate=shopRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
        Optional<List<JentleProducts>> JCart= Optional.of(shopRepository.findAll());
        List<JentleProducts> jCartName=JCart.get();
        List<JentleProducts> JCartSortName;
        if(ads.equalsIgnoreCase("asc")) {
            JCartSortName = jCartName.stream().sorted(Comparator.comparing(JentleProducts::getName)).collect(Collectors.toList());
        }else if(shopRepository.count() == 0){
            throw new UserDefinedException("No Item In Database");
        }
        else if(ads.equalsIgnoreCase("desc")){
            JCartSortName = jCartName.stream().sorted(Comparator.comparing(JentleProducts::getName).reversed()).collect(Collectors.toList());
        }
        else {
            throw new UserIllegalArgumentException("Invalid sorting order. Valid values are 'asc' or 'desc'.");
        }
        return JCartSortName;
    }

    @Override//18
    public List<JentleProducts> OrderJProductsByReviewCount(String ads) {
        // List<JentleProducts> JCartRate=shopRepository.findAll(Sort.by(Sort.Direction.ASC,"reviewCount"));
        Optional<List<JentleProducts>> JCart= Optional.of(shopRepository.findAll());
        List<JentleProducts> jCartReviewCount=JCart.get();
        List<JentleProducts> JCartSortReviewCount;
        if(shopRepository.count() == 0){
            throw new UserDefinedException("No Item In Database");
        }else if(ads.equalsIgnoreCase("asc")) {
            JCartSortReviewCount = jCartReviewCount.stream().sorted(Comparator.comparing(JentleProducts::getReviewCount)).collect(Collectors.toList());
        }
        else if(ads.equalsIgnoreCase("desc")){
            JCartSortReviewCount = jCartReviewCount.stream().sorted(Comparator.comparing(JentleProducts::getReviewCount).reversed()).collect(Collectors.toList());
        }
        else {
            throw new UserIllegalArgumentException("Invalid sorting order. Valid values are 'asc' or 'desc'.");
        }
        return JCartSortReviewCount;
    }

    @Override//19
    public String deleteProductsById(Integer prodId) {
        if(!shopRepository.existsById(prodId)){
            throw new UserDefinedException("Id not Found");
        }
        if (shopRepository.existsById(prodId)) {
            shopRepository.deleteById(prodId);
            return "Deleted Successfully";
        } else
            try {
                throw new RuntimeException("This ID is not available");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
    }
    @Override
    public PurchaseOrder buyAllProductsFromJCart(String country, String promoCode, String emailId) {
        float GST = 0.14F;
        double finalAmount;
        double discount;
        double countryPrice = 0.0;
        double totalBuyAmount = 0.0;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        double roundedNumber;

        List<JentleCart> jCartList = cartRepository.findAll();

        //Filtering price through emailId for specific customer ordered items
        List<Float> prices = jCartList.stream()
                .filter(cart -> cart.getEmailId().equals(emailId))
                .map(cart -> cart.getPrice()*cart.getQuantity())
                .collect(Collectors.toList());

        List<JentleProducts> jProduct = shopRepository.findAll();
//converts each price to double using .maptoDouble and add each values.
        double priceSum = prices.stream().mapToDouble(price -> price).sum();
        // double priceSum = prices.stream().mapToDouble(price -> price.doubleValue()).sum();

        List<DeliveryChargeDetails> deliveryChargeList = deliveryExpenseRepository.findAll();
        //filtering delivery expense price through countryname
        //'::' method reference operator
        Double optionalCountryPrice = deliveryChargeList.stream()
                .filter(details -> details.getCountryName().equalsIgnoreCase(country))
                .mapToDouble(DeliveryChargeDetails::getDeliveryExpense)
                .findFirst().getAsDouble();

        countryPrice = optionalCountryPrice;

        totalBuyAmount = priceSum + (GST * priceSum) + countryPrice;

        if (priceSum > 200) {
            switch (promoCode) {
                case "cjspa1":
                    discount = priceSum * 0.45;
                    finalAmount = totalBuyAmount - discount;
                    roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                    break;
                case "cjspa2":
                    discount = priceSum * 0.26;
                    finalAmount = totalBuyAmount - discount;
                    roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                    break;
                case "cjspa3":
                    finalAmount = totalBuyAmount - countryPrice;
                    roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                    break;
                case "cjspa4":
                    discount = priceSum * 0.30;
                    finalAmount = totalBuyAmount - discount;
                    roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                    break;
                case "cjspa5":
                    discount = priceSum * 0.10;
                    finalAmount = totalBuyAmount - discount;
                    roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                    break;
                default:
                    finalAmount = totalBuyAmount;
                    roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                    break;
            }
        } else {
            finalAmount = totalBuyAmount;
            roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
        }

        Optional<List<CustomerInfo>> custInfoOptional= Optional.ofNullable(customerRepository.findAll());

        List<CustomerInfo> custInfo=custInfoOptional.get();
        CustomerInfo custDiscount=custInfo.stream()
                .filter(j->j.getEmailId().equals(emailId)).findFirst().get();
        if(custDiscount.getReferralCount()>0){
            double referralDiscount=priceSum*0.05;
            finalAmount=finalAmount-referralDiscount;
            roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
            custDiscount.setReferralCount(custDiscount.getReferralCount()-1);
        }
      String custName = jCartList.stream()
              .filter(cart -> cart.getEmailId().equals(emailId))
              .map(cart -> cart.getCustomer_Name()).findFirst().toString();
      //  String custName=custDiscount.getCustomerName();


        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setCustomerName(custName);
        purchaseOrder.setTotalPrice(roundedNumber);
        purchaseOrder.setOrderDate(LocalDate.now());
        purchaseOrder.setEmailId(emailId);
        PurchaseOrder savedPurchaseOrder = orderRepository.save(purchaseOrder);

        // Save the purchase order to retrieve the generated ID

        // Send email to the customer
        try {
            //This line creates a new MimeMessage object using the createMimeMessage() method provided by the JavaMailSender implementation.
            // The MimeMessage represents an email message and provides methods to set various properties such as recipients,
            // subject, content, attachments, etc. This object acts as a container for all the information required to send an email.
            MimeMessage message = mailSender.createMimeMessage();
            //Decides whether a mail can have attachments or not
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(emailId);
            helper.setSubject("Purchase Order Confirmation");
            //passing parameter and true indicates html
            //true, indicates that the content provided in the first parameter is in HTML format.
            // By passing true, you are specifying that the email content should be interpreted as HTML markup
            // rather than plain text.
            // This allows you to include formatting, links, images, and other HTML elements in the email.
            helper.setText(getPurchaseConfirmationEmailContent(savedPurchaseOrder), true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new UserDefinedException("An error in mail code");
        }
//filtering items to delte from cart using stream
        List<JentleCart> itemsToDelete = jCartList.stream()
                .filter(cart -> cart.getEmailId().equals(emailId))
                .collect(Collectors.toList());

        for (JentleCart item : itemsToDelete) {
            cartRepository.delete(item);

            JentleProducts product = jProduct.stream()
                    .filter(p -> p.getProduct_id().equals(item.getProduct_id()))
                    .findAny().orElse(null);

            if (product != null) {
                int remainingStock = product.getStock() - item.getQuantity();
                if (remainingStock >= 0) {
                    product.setStock(remainingStock);
                    shopRepository.save(product);
                } else {
                    throw new UserDefinedException("While handling stock some error occured");
                }
            }
        }
        return savedPurchaseOrder;
    }
  /*@Override
  public PurchaseOrder buyAllProductsFromJCart(String country, String promoCode, String emailId) {
      float GST = 0.14F;
      double finalAmount;
      double discount;
      double countryPrice = 0.0;
      double totalBuyAmount = 0.0;
      DecimalFormat decimalFormat = new DecimalFormat("#.##");
      double roundedNumber;

      List<JentleCart> jCartList = cartRepository.findAll();

      //Filtering price through emailId for specific customer ordered items
      List<Float> prices = jCartList.stream()
              .filter(cart -> cart.getEmailId().equals(emailId))
              .map(cart -> cart.getPrice()*cart.getQuantity())
              .collect(Collectors.toList());

      List<JentleProducts> jProduct = shopRepository.findAll();
//converts each price to double using .maptoDouble and add each values.
      double priceSum = prices.stream().mapToDouble(price -> price).sum();
     // double priceSum = prices.stream().mapToDouble(price -> price.doubleValue()).sum();

      List<DeliveryChargeDetails> deliveryChargeList = deliveryExpenseRepository.findAll();
      //filtering delivery expense price through countryname
      //'::' method reference operator
      Double optionalCountryPrice = deliveryChargeList.stream()
              .filter(details -> details.getCountryName().equalsIgnoreCase(country))
              .mapToDouble(DeliveryChargeDetails::getDeliveryExpense)
              .findFirst().getAsDouble();

       countryPrice = optionalCountryPrice;

      totalBuyAmount = priceSum + (GST * priceSum) + countryPrice;

      if (priceSum > 200) {
          switch (promoCode) {
              case "cjspa1":
                  discount = priceSum * 0.45;
                  finalAmount = totalBuyAmount - discount;
                  roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                  break;
              case "cjspa2":
                  discount = priceSum * 0.26;
                  finalAmount = totalBuyAmount - discount;
                  roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                  break;
              case "cjspa3":
                  finalAmount = totalBuyAmount - countryPrice;
                  roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                  break;
              case "cjspa4":
                  discount = priceSum * 0.30;
                  finalAmount = totalBuyAmount - discount;
                  roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                  break;
              case "cjspa5":
                  discount = priceSum * 0.10;
                  finalAmount = totalBuyAmount - discount;
                  roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                  break;
              default:
                  finalAmount = totalBuyAmount;
                  roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
                  break;
          }
      } else {
          finalAmount = totalBuyAmount;
          roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
      }

      Optional<List<JentleCart>> custInfoOptional= Optional.ofNullable(cartRepository.findAll());

      List<JentleCart> custInfo=custInfoOptional.get();
      JentleCart custDiscount=custInfo.stream()
              .filter(j->j.getEmailId().equals(emailId)).findFirst().get();
      if(custDiscount.getReferralCount()>0){
          double referralDiscount=priceSum*0.05;
          finalAmount=finalAmount-referralDiscount;
          roundedNumber = Double.parseDouble(decimalFormat.format(finalAmount));
          custDiscount.setReferralCount(custDiscount.getReferralCount()-1);
      }
      String custName = jCartList.stream()
              .filter(cart -> cart.getEmailId().equals(emailId))
              .map(cart -> cart.getCustomer_Name()).findFirst().toString();
      String custName=custDiscount.getCustomerName();


      PurchaseOrder purchaseOrder = new PurchaseOrder();
      purchaseOrder.setCustomerName(custName);
      purchaseOrder.setTotalPrice(roundedNumber);
      purchaseOrder.setOrderDate(LocalDate.now());
      purchaseOrder.setEmailId(emailId);
      PurchaseOrder savedPurchaseOrder = orderRepository.save(purchaseOrder);

      // Save the purchase order to retrieve the generated ID

      // Send email to the customer
      try {
          //This line creates a new MimeMessage object using the createMimeMessage() method provided by the JavaMailSender implementation.
          // The MimeMessage represents an email message and provides methods to set various properties such as recipients,
          // subject, content, attachments, etc. This object acts as a container for all the information required to send an email.
          MimeMessage message = mailSender.createMimeMessage();
          //Decides whether a mail can have attachments or not
          MimeMessageHelper helper = new MimeMessageHelper(message, true);
          helper.setTo(emailId);
          helper.setSubject("Purchase Order Confirmation");
          //passing parameter and true indicates html
          //true, indicates that the content provided in the first parameter is in HTML format.
          // By passing true, you are specifying that the email content should be interpreted as HTML markup
          // rather than plain text.
          // This allows you to include formatting, links, images, and other HTML elements in the email.
          helper.setText(getPurchaseConfirmationEmailContent(savedPurchaseOrder), true);
          mailSender.send(message);
      } catch (MessagingException e) {
          throw new UserDefinedException("An error in mail code");
      }
//filtering items to delete from cart using stream
      List<JentleCart> itemsToDelete = jCartList.stream()
              .filter(cart -> cart.getEmailId().equals(emailId))
              .collect(Collectors.toList());

      for (JentleCart item : itemsToDelete) {
          cartRepository.delete(item);

          JentleProducts product = jProduct.stream()
                  .filter(p -> p.getProduct_id().equals(item.getProduct_id()))
                  .findAny().orElse(null);

          if (product != null) {
              int remainingStock = product.getStock() - item.getQuantity();
              if (remainingStock >= 0) {
                  product.setStock(remainingStock);
                  shopRepository.save(product);
              } else {
                  throw new UserDefinedException("While handling stock some error occured");
              }
          }
      }

      return savedPurchaseOrder;
  }*/

    public String getPurchaseConfirmationEmailContent(PurchaseOrder purchaseOrder) {
      //you can create a Context object and populate it with variables and data that will be accessible within the templates.
        // These variables can then be used in the template to dynamically generate the content.
        //It is also a type of container
        Context context = new Context();
        //sets a variable named "purchaseOrder" in the Context object.
        context.setVariable("purchaseOrder", purchaseOrder);

        return templateEngine.process("shoppingBillTemplate", context);
    }
    @Override//21
    public List<JentleProducts> OrderJProductsByRatingsAndName(String ads) {
        //   List<JentleProducts> JCartRate=shopRepository.findAll(Sort.by(Sort.Direction.ASC,"ratings"));
        Optional<List<JentleProducts>> JCart= Optional.of(shopRepository.findAll());
        List<JentleProducts> jCartRatings = JCart.get();
        List<JentleProducts> JCartSortRatings;
        if(shopRepository.count() == 0){
            throw new UserDefinedException("No Item In Database");
        } else if(ads.equalsIgnoreCase("asc")) {
            JCartSortRatings = jCartRatings.stream().sorted(Comparator.comparing(JentleProducts::getRatings).thenComparing(JentleProducts::getName)).collect(Collectors.toList());
        }
        else if(ads.equalsIgnoreCase("desc")){
            JCartSortRatings = jCartRatings.stream().sorted(Comparator.comparing(JentleProducts::getRatings).thenComparing(JentleProducts::getName).reversed()).collect(Collectors.toList());

        }
        else {
            throw new UserIllegalArgumentException("Invalid sorting order. Valid values are 'asc' or 'desc'.");
        }
        return JCartSortRatings;
    }


    /*@Override
    public CustomerInfo saveCustomerDetails(CustomerInfo customerDetails) {
        Optional<List<CustomerInfo>> customerDetailOptional= Optional.of(customerRepository.findAll());

        if(customerDetailOptional.isPresent()){
            List<CustomerInfo> customerDetail=customerDetailOptional.get();
            for(CustomerInfo customerInfo: customerDetail){
                if(customerInfo.getPhoneNumber().equals(customerDetails.getPhoneNumber()) || customerInfo.getAddress().equals(customerDetails.getAddress())|| customerInfo.getEmailId().equalsIgnoreCase(customerDetails.getEmailId())) {
                    throw new UserDefinedException("Already Found In Database");
                }
            }
        }
        return customerRepository.save(customerDetails);
    }*/




   @Override
   public CustomerInfo saveCustomerDetails(CustomerInfo customerDetails,String referCode) {
       Optional<List<CustomerInfo>> customerDetailOptional = Optional.of(customerRepository.findAll());
List<CustomerInfo> customerDetail=customerDetailOptional.get();
       CustomerInfo custDetail=customerDetail.stream().filter(j->j.getReferralCode().equals(referCode)).findFirst().get();
if(custDetail!=null){
    custDetail.setReferralCount(custDetail.getReferralCount()+1);
}
      boolean isDuplicate = customerDetail.stream()
            .anyMatch(customerInfo ->
                    customerInfo.getCustomerName().equals(customerDetails.getCustomerName()) ||
                    customerInfo.getPhoneNumber().equals(customerDetails.getPhoneNumber()) ||
                    customerInfo.getAddress().equals(customerDetails.getAddress()) ||
                    customerInfo.getEmailId().equalsIgnoreCase(customerDetails.getEmailId())
            );

    if (isDuplicate) {
        throw new UserDefinedException("Already Found In Database");
    }
       sendCongratulationsEmail(customerDetails);
       return customerRepository.save(customerDetails);
   }
    public void sendCongratulationsEmail(CustomerInfo customerInfo) {
        String recipientEmail = customerInfo.getEmailId();
        String subject = "Congratulations on Signing Up!";
        String messageText = "Dear " + customerInfo.getCustomerName() + ",\n\n"
                + "Congratulations on signing up for our account! We are thrilled to welcome you to our community.\n\n"
                + "Here are your account details:\n"
                + "Name: " + customerInfo.getCustomerName() + "\n"
                + "Email: " + customerInfo.getEmailId() + "\n"
                + "Phone: " + customerInfo.getPhoneNumber() + "\n"
                + "Address: " + customerInfo.getAddress() + "\n\n"
                + "Thank you for choosing our services. If you have any questions or need assistance, please feel free to reach out to us.\n\n"
                + "Best regards,\n"
                + "CHANEL";
//is provided by the Spring Framework and represents a simple email message.
// It is a straightforward and lightweight way to send basic text-based emails without any attachments or advanced features.
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(messageText);
//// It creates an instance of the JavaMailSenderImpl class,
        // which represents an email sender implementation in the JavaMail API.
        // It allows you to send emails programmatically by configuring the necessary properties
        // and using its methods for sending email messages.
        mailSender.send(message);
    }

    @Override
    public DeliveryChargeDetails saveDeliveryAndDetails(DeliveryChargeDetails deliveryChargeDetails) {
        Optional<List<DeliveryChargeDetails>> deliveryExpense= Optional.of(deliveryExpenseRepository.findAll());
        if(deliveryExpense.isPresent()){
            List<DeliveryChargeDetails> dCharge=deliveryExpense.get();
            for(DeliveryChargeDetails deliveryExp: dCharge){
              if(deliveryExp.getCountryName().equals(deliveryChargeDetails.getCountryName())) {
                    throw new UserDefinedException("Already Found In Database");
                }
            }
        }
        return deliveryExpenseRepository.save(deliveryChargeDetails);
        }

  /* public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }*/


   /* public void sendPurchaseOrderEmail(PurchaseOrder purchaseOrder, String recipientEmail) throws MessagingException {
        // Prepare the Thymeleaf context
        Context context = new Context();
        context.setVariable("purchaseOrder", purchaseOrder);

        // Process the HTML template
        String emailBody = templateEngine.process("shoppingBillTemplate", context);

        // Send the email
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientEmail);
        helper.setSubject("Your Purchase Order");
        helper.setText(emailBody, true);
        mailSender.send(message);
    }*/
   public void sendReferralCode(String recipientEmail, String hostEmail) {
       Optional<List<CustomerInfo>> referralOptional = Optional.ofNullable(customerRepository.findByEmailId(hostEmail));
       if (referralOptional.isPresent()) {
           List<CustomerInfo> referralList = referralOptional.get();   //HOST EMAILGET FROM CUSTOMERINFO DATABASE
           if (!referralList.isEmpty()) {
               CustomerInfo referral = referralList.get(0);
               Optional<List<CustomerInfo>> recipientCheckOptional = Optional.ofNullable(customerRepository.findByEmailId(recipientEmail));
               if (recipientCheckOptional.isPresent() && !recipientCheckOptional.get().isEmpty()) {
                   throw new UserDefinedException("This email address is already signed into our website");
               }
               String referralCode = referral.getReferralCode();
               String customer = referral.getCustomerName();
               String subject = "Invitation: Create an Account with Referral Code";
               String messageText = "Dear Friend,\n\n"
                       + "I would like to invite you to create an account on our platform using my referral code. With this referral code, you will receive special benefits!\n\n"
                       + "Referral Code: " + referralCode + "\n\n"
                       + "To create an account, please visit our registration page: [Insert Registration Page URL]\n\n"
                       + "Thank you for using my referral code. Let me know if you have any questions!\n\n"
                       + "Best regards,\n"
                       + customer;

               SimpleMailMessage message = new SimpleMailMessage();
               message.setTo(recipientEmail);
               message.setSubject(subject);
               message.setText(messageText);

               mailSender.send(message);
           }
       }
   }
}



