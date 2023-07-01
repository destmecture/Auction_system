package com.example.auction.repository;

import com.example.auction.dto.BidDTO;
import com.example.auction.entity.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BidRepository extends CrudRepository<Bid, Integer> {
    Optional<Bid> findFirstByLot_IdOrderByOffsetDateTimeAsc(int lotId);



    @Query("SELECT b.name, max(b.offsetDateTime) FROM Bid b WHERE b.lot.id = :id GROUP BY b.name HAVING count(b.name) =(SELECT count(b.name) FROM B b WHERE b.lot_id = :id GROUP BY b.name ORDER BY cnt DESC LIMIT 1)")
    BidDTO maxCountBidder(int id);



    List<Bid> findAllByLot_Id(int lotId);

}
