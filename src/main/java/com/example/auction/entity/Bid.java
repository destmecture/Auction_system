package com.example.auction.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;

@Entity
@Table(name = "bids")
@Getter
@Setter
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String name;
    @CreationTimestamp
    @Column(name = "Date_time", nullable = false, updatable = false)
    private OffsetDateTime offsetDateTime;
    @ManyToOne
    @JoinColumn(name = "lot_id")
    private Lot lot;


}
