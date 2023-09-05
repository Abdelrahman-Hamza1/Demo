package com.demo.auction.auctions;

import com.demo.auction.Status;
import com.demo.auction.bid.Bid;
import com.demo.auction.bid.BidRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service@AllArgsConstructor
public class AuctionService {

    AuctionRepository auctionRepository;
    BidRepository bidRepository;

    /*
    Handle Auctions & Bids on them
    */
    public void createAuction(String title, int bookId, int userId){
        auctionRepository.save(new Auction(
                title,bookId,userId, new ArrayList<>(), Status.ONGOING
        ));
    }

    public List<Auction> getAuctionsByTitleContaining(String title){
        return  auctionRepository.getAuctionByTitleContaining(title);
    }

    public void addBidOnAuction(int auctionId, Bid bid){
        Auction auc = auctionRepository.findById(auctionId).get();
        auc.addBid(bid);

        auctionRepository.save(auc);
    }

    public void itemSold(int auctionId){
        Auction auc = auctionRepository.findById(auctionId).get();
        auc.setStatus(Status.SOLD);
        auctionRepository.save(auc);
    }

    public void removeBidById(int auctionId, int bidId){
        Auction auc = auctionRepository.findById(auctionId).get();
        auc.removeBidById(bidId);
        auctionRepository.save(auc);
    }
}
