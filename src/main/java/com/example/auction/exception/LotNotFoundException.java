package com.example.auction.exception;

public class LotNotFoundException extends Exception {
    public LotNotFoundException(String message){
        super(message);
    }
}
