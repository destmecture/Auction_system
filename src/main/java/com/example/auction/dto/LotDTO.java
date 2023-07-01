package com.example.auction.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LotDTO {
    private int id;
    private Status status;
    private String title;
    private String description;
    private int startPrice;
    private int bidPrice;

}
