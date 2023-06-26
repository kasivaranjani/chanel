package com.example.chanel.controller;

import com.example.chanel.userException.UserDefinedException;
import com.example.chanel.userException.UserIllegalArgumentException;
import com.example.chanel.userException.UserRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {
    @ExceptionHandler(UserRuntimeException.UserCreatedException.class)
    public ResponseEntity<String> handleUserDefinedException(UserRuntimeException.UserCreatedException ex){
        String errorMessage= ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
    @ExceptionHandler(UserIllegalArgumentException.class)
    public ResponseEntity<String> handleUserDefinedException(UserIllegalArgumentException ex){
        String errorMessage= ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }

    @ExceptionHandler(UserDefinedException.class)
    public ResponseEntity<String> handleUserDefinedException(UserDefinedException ex){
        String errorMessage= ex.getMessage();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
    }
}
