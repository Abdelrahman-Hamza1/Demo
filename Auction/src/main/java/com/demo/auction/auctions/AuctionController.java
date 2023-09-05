package com.demo.auction.auctions;


import com.demo.auction.bid.Bid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/Auction")
@AllArgsConstructor
public class AuctionController {

    AuctionService auctionService;
//    RestTemplate restTemplate;
//    return restTemplate.getForObject("https://BOOKS/Book/IsValid/2", Boolean.class);

    // QUESTION : DO WE VERIFY USER ID & BOOK ID ?? ?? ??

    @PostMapping("/CreateAuction/{title}/{bookId}/{userId}")
    public void createAuction(@PathVariable  String title
            ,@PathVariable int bookId
            ,@PathVariable int userId){
        auctionService.createAuction(title,bookId,userId);
    }

    @GetMapping("/GetAuctions/{title}")
    public List<Auction> getAuctionsByTitleContaining(@PathVariable  String title){
        return auctionService.getAuctionsByTitleContaining(title);
    }

    @PostMapping("/AddBid/{auctionId}/{amount}/{userId}/{comment}")
    public void addBidOnAuction(@PathVariable int auctionId,@PathVariable double amount,
                                @PathVariable int userId,@PathVariable String comment){
        auctionService.addBidOnAuction(auctionId, new Bid(amount,userId,
                "today's date", comment));
    }

    @PostMapping("/AddNewBookAuction/")
    public void addBookThatDoesntExist(){
        // Send book to library database, request for approval, when approved -> add auction after book addition.

    }

    @PutMapping("/SoldItem/{auctionId}")
    public void auctionEnded(@PathVariable int auctionId){
        auctionService.itemSold(auctionId);
    }

    @DeleteMapping("/DeleteBid/{auctionId}/{bidId}")
    public void deleteBid(@PathVariable  int auctionId,@PathVariable int bidId){
        auctionService.removeBidById(auctionId,bidId);
    }

}
