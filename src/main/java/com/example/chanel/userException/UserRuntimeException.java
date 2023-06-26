package com.example.chanel.userException;

public class UserRuntimeException {

    public static class UserCreatedException extends RuntimeException{
        public UserCreatedException(String message) {
            super(message);
        }
    }
}
