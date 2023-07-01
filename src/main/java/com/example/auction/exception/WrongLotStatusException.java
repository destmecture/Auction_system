package com.example.auction.exception;

public class WrongLotStatusException extends Exception {
    public WrongLotStatusException(String message){
        super(message);
    }
}
