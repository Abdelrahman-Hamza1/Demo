package com.demo.auction.auctions;

import com.demo.auction.bid.Bid;
import com.demo.auction.bid.BidRepository;
import com.demo.auction.books.AuctionedBook;
import com.demo.auction.books.Status;
import com.demo.auction.messaging.Message;
import com.demo.auction.messaging.RabbitMQConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service@AllArgsConstructor
public class AuctionService {

    AuctionRepository auctionRepository;
    BidRepository bidRepository;
    RabbitTemplate template;

    /*
    Handle Auctions & Bids on them
    */
    public List<Auction> getAllAuctions(){
        return auctionRepository.findAll();
    }
    public void createAuction(String title, int bookId, String userId){
        auctionRepository.save(new Auction(
                title,bookId,userId, new ArrayList<>(), AuctionStatus.ONGOING
        ));
    }

    public List<Auction> getAuctionsByTitleContaining(String title){
        return  auctionRepository.getAuctionByTitleContaining(title);
    }

    public void addBidOnAuction(int auctionId, Bid bid){
        // CHECK STATUS !!!!!!
        Auction auc = auctionRepository.findById(auctionId).get();
        auc.addBid(bid);

        auctionRepository.save(auc);
    }

    public void itemSold(int auctionId){
        Auction auc = auctionRepository.findById(auctionId).get();
        auc.setStatus(AuctionStatus.SOLD);
        auctionRepository.save(auc);
    }

    public void removeBidById(int auctionId, int bidId){
        Auction auc = auctionRepository.findById(auctionId).get();
        // Could verify if  bid belongs to username.
        auc.removeBidById(bidId);
        auctionRepository.save(auc);
    }

    public void addAuctionForNewBook(String name, String author, String title, String username){
        Auction auction = new Auction(title, -1, username,
                new ArrayList<>(), AuctionStatus.PENDING);
        auctionRepository.save(auction);

        Message message = new Message();
        message.setBook(new AuctionedBook(name,author,Status.PENDING, auction.getId()));
        message.setMessageId(UUID.randomUUID().toString());
        template.convertAndSend(RabbitMQConfiguration.EXCHANGE,
                RabbitMQConfiguration.ROUTING_KEY_ONE, message);
    }

    // ONLY ADMIN ACCESS THIS.
    public void confirmAuction(int auctionId){
        Auction auction = auctionRepository.findById(auctionId).get();
        auction.setStatus(AuctionStatus.ONGOING);
        auctionRepository.save(auction);
    }

}
