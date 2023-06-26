package com.example.chanel.userException;

public class UserIllegalArgumentException extends RuntimeException {
    public UserIllegalArgumentException(String message){
        super(message);
    }

    public String nullValueException(){
        return getMessage();
    }
}

