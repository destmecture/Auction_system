package com.example.auction.mapper;

import com.example.auction.dto.BidDTO;
import com.example.auction.entity.Bid;
import org.springframework.stereotype.Component;

@Component
public class BidMapper {
    public BidDTO toDto(Bid bid){
        BidDTO bidDTO = new BidDTO();
        bidDTO.setBidderName(bid.getName());
        bidDTO.setBidDate(bid.getOffsetDateTime());
        return bidDTO;
    }
}
