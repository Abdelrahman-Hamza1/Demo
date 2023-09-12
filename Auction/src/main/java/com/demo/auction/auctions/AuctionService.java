package com.demo.auction.auctions;

import com.demo.auction.bid.Bid;
import com.demo.auction.bid.BidRepository;
import com.demo.auction.books.AuctionedBook;
import com.demo.auction.books.Status;
import com.demo.auction.messaging.Message;
import com.demo.auction.messaging.RabbitMQConfiguration;
import com.demo.auction.models.Auction;
import com.demo.auction.models.AuctionStatus;
import com.demo.auction.models.Communications;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service@AllArgsConstructor
public class AuctionService {

    AuctionRepository auctionRepository;
    BidRepository bidRepository;
    RabbitTemplate template;
    Communications communications;

    public List<Auction> getAllAuctions(){
        return auctionRepository.findAll();
    }
    public void createAuction(String title, int bookId, String username){
        auctionRepository.save(new Auction(
                title,bookId,username, new ArrayList<>(), AuctionStatus.ONGOING
        ));
    }

    public List<Auction> getAuctionsByTitleContaining(String title){
        return  auctionRepository.getAuctionByTitleContaining(title);
    }

    public Auction addBidOnAuction(int auctionId, Bid bid){
        Optional<Auction> auc = auctionRepository.findById(auctionId);
        if(auc.isPresent()){
            Auction auction = auc.get();
            if(auction.getStatus() == AuctionStatus.PENDING || auction.getStatus() == AuctionStatus.SOLD){
                return null;
            }
            auction.addBid(bid);
            return auctionRepository.save(auction);
        }
        return null;
    }

//Possible update to itemSold method: Let user select the Bid he's selling to, attach it in request
// We can verify that the bid actually belongs to the auction & if it does, mark the Bidders' Username
//as the Buyer Username
    public Auction itemSold(int auctionId, String username){
        Optional<Auction> auc = auctionRepository.findById(auctionId);
        if(auc.isPresent()){
            Auction auction = auc.get();
            if(auction.getUsername().equalsIgnoreCase(username) || communications.isAdmin(username)){
                auction.setStatus(AuctionStatus.SOLD);
                return auctionRepository.save(auction);
            }
        }
        return null;
    }

    public Auction removeBidById(int auctionId, int bidId, String username){
        Optional<Auction> auction = auctionRepository.findById(auctionId);
        Optional<Bid> optionalBid = bidRepository.findById(bidId);
        if(auction.isPresent() && optionalBid.isPresent()){
            Auction auc = auction.get();
            Bid bid = optionalBid.get();
            if(bid.getUsername().equals(username) || communications.isAdmin(username)){
                auc.removeBidById(bid.getId());
                return auctionRepository.save(auc);
            }
        }
        return null;
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

    // ONLY ADMIN ACCESS THIS. -> Block it from the gateway -> only Book service will call it
    // and book service will only allow an admin to make the call.
    public Auction confirmAuction(int auctionId){
        Optional<Auction> auc = auctionRepository.findById(auctionId);
        if(auc.isPresent()){
            Auction auction = auc.get();
            auction.setStatus(AuctionStatus.ONGOING);
            return auctionRepository.save(auction);
        }
        return null;
    }
}
