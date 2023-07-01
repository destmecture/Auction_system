package com.example.auction.service;

import ch.qos.logback.core.recovery.ResilientOutputStreamBase;
import com.example.auction.dto.CreateLotDTO;
import com.example.auction.dto.FullLotDTO;
import com.example.auction.dto.LotDTO;
import com.example.auction.dto.Status;
import com.example.auction.entity.Bid;
import com.example.auction.entity.Lot;
import com.example.auction.exception.LotNotFoundException;
import com.example.auction.mapper.BidMapper;
import com.example.auction.mapper.LotMapper;
import com.example.auction.repository.BidRepository;
import com.example.auction.repository.LotRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LotService {
    private final LotRepository lotRepository;
    private final  BidRepository bidRepository;
    private final LotMapper lotMapper;
    private final BidMapper bidMapper;

    @SneakyThrows
    public FullLotDTO getFullLot(int id){

        Lot lot = lotRepository.findById(id).orElseThrow(()-> new LotNotFoundException("Лот не найден"));
        List<Bid> bidList = bidRepository.findAllByLot_Id(id);
        Bid lastBid = bidList.stream()
                .max(Comparator.comparing(Bid::getOffsetDateTime))
                .orElse(new Bid());
        int currentPrice = bidList.size()*lot.getBidPrice()+lot.getStartPrice();
        return lotMapper.toFullLotDTO(lot, currentPrice,bidMapper.toDto(lastBid));
    }


    @SneakyThrows
    public void startLotBidding(int id) {
        Lot lot = lotRepository.findById(id).orElseThrow(()-> new LotNotFoundException("Лот не найден"));
        lot.setStatus(Status.STARTED);
        lotRepository.save(lot);
    }

    @SneakyThrows
    public void stopLotBidding(int id) {
        Lot lot = lotRepository.findById(id).orElseThrow(()-> new LotNotFoundException("Лот не найден"));
        lot.setStatus(Status.STOPPED);
        lotRepository.save(lot);
    }

    public LotDTO createLot(CreateLotDTO createLotDTO) {
        return lotMapper.toLotDTO(
                lotRepository.save(lotMapper.fromCreateLotDTOtoEntity(createLotDTO))
        );
    }

    public List<LotDTO> findLots(@Nullable Status status, int page) {
       Pageable pageable = PageRequest.of(page, 10);
        return Optional.ofNullable(status)
                .map(x->lotRepository.findAllByStatus(x, pageable))
                .orElseGet(()->lotRepository.findAll(pageable))
                .stream()
                .map(lotMapper::toLotDTO)
                .collect(Collectors.toList());
    }

    public byte[] getCSVFile() {
        lotRepository.getAllLotsForCsv().forEach(x-> System.out.println(Arrays.toString(x)));


        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                .setHeader("id","title","status","lastBidder","currentPrice")
                .build();


        try(
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        CSVPrinter printer = new CSVPrinter(new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8), csvFormat)
        ){

            for (Object[] obj: lotRepository.getAllLotsForCsv()) {
                printer.printRecord(obj);
            }
            printer.flush();
            return byteArrayOutputStream.toByteArray();
        }catch (IOException exception){
            exception.printStackTrace();
        }
        return null;

    }
}
