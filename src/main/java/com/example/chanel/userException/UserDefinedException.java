package com.example.chanel.userException;

public class UserDefinedException extends RuntimeException{
    public UserDefinedException(){
        super();
    }
    /* public UserDefinedException(String message){
         super(String.format("User account number ending with %s is not found in our Database",message));
     }*/
    public UserDefinedException(String message){
        super(message);
    }
}
