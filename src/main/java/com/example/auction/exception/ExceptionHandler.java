package com.example.auction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> lotNotFoundHandler(LotNotFoundException lotNotFoundException){
        return new ResponseEntity<>(lotNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> bidNotFoundHandler(BidNotFoundException bidNotFoundException){
        return new ResponseEntity<>(bidNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler
    public ResponseEntity<?> WrongLotStatusHandler(WrongLotStatusException wrongLotStatusException){
        return new ResponseEntity<>(wrongLotStatusException.getMessage(), HttpStatus.BAD_REQUEST);
    }



}
