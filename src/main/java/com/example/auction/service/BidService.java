package com.example.auction.service;

import com.example.auction.dto.BidDTO;
import com.example.auction.dto.BidderDTO;
import com.example.auction.dto.Status;
import com.example.auction.entity.Bid;
import com.example.auction.entity.Lot;
import com.example.auction.exception.BidNotFoundException;
import com.example.auction.exception.LotNotFoundException;
import com.example.auction.exception.WrongLotStatusException;
import com.example.auction.mapper.BidMapper;
import com.example.auction.repository.BidRepository;
import com.example.auction.repository.LotRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BidService {
    private final BidRepository bidRepository;
    private final LotRepository lotRepository;

    private final BidMapper bidMapper;

    @SneakyThrows
    public BidDTO getFirstBidder(int id){
        if(lotRepository.existsById(id)) {
            return bidRepository.findFirstByLot_IdOrderByOffsetDateTimeAsc(id)
                    .map(bidMapper::toDto)
                    .orElseThrow(()->new BidNotFoundException("Ставка не найдена"));
        }else{
            throw new LotNotFoundException("Лот не найден");
        }
    }

    @SneakyThrows
    public BidDTO getMostFrequentBidder(int id){
        if(lotRepository.existsById(id)){
            return bidRepository.maxCountBidder(id);
        }else{
            throw new LotNotFoundException("Лот не найден");
        }


    }

    @SneakyThrows
    public void createBid(int id, BidderDTO bidderDTO) {
        Lot lot = lotRepository.findById(id).orElseThrow(()-> new LotNotFoundException("Лот не найден"));
        if(lot.getStatus()== Status.CREATED||lot.getStatus()==Status.STOPPED){
            throw new WrongLotStatusException("Лот в неверном статусе");
        }
        Bid bid = new Bid();
        bid.setName(bidderDTO.getName());
        bid.setLot(lot);
        bidRepository.save(bid);
    }
}
