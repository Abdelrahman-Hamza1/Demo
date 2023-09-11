package com.demo.auction.auctions;

import com.demo.auction.models.Auction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction,Integer> {
    List<Auction> getAuctionByTitleContaining(String title);
}
