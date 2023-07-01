package com.example.auction.repository;

import com.example.auction.dto.Status;
import com.example.auction.entity.Lot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LotRepository extends JpaRepository<Lot, Integer> {

    Page<Lot> findAllByStatus(Status status, Pageable pageable);

    @Query(value = "SELECT l.id, l.title, l.status,last_time.name, count(b)*l.bid_price+l.start_price\n" +
            " FROM lots l JOIN bids b on l.id = b.lot_id LEFT JOIN (SELECT x.name, x.lot_id, x.date_time FROM bids x RIGHT JOIN\n" +
            "(SELECT d.lot_id, max(d.date_time) as time FROM bids d GROUP BY d.lot_id) as max_time ON max_time.lot_id = x.lot_id\n" +
            "and time = x.date_time) as last_time on l.id = last_time.lot_id\n" +
            "GROUP BY l.id, l.title, l.status, last_time.name;", nativeQuery = true)
    List<Object[]> getAllLotsForCsv();




}
