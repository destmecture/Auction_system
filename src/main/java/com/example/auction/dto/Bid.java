package com.example.auction.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class Bid {
    private String bidderName;
    private OffsetDateTime bidDate;

}
