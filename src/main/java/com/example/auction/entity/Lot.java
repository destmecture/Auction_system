package com.example.auction.entity;

import com.example.auction.dto.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lots")
@Getter
@Setter
public class Lot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Enumerated(EnumType.STRING)
    @Column
    private Status status;
    @Column(length = 64, nullable = false)
    private String title;
    @Column(length = 4096, nullable = false)
    private String description;
    @Column(name = "start_price", nullable = false)
    private int startPrice;
    @Column(name = "bid_price",nullable = false)
    private int bidPrice;

}
