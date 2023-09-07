package com.demo.auction.auctions;


import com.demo.auction.bid.Bid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Auction")
@AllArgsConstructor
public class AuctionController {

    AuctionService auctionService;

    @GetMapping("/GetAll")
    public List<Auction> getAll(){
        return auctionService.getAllAuctions();
    }

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

    @PostMapping("/AddNewBookAuction/{title}/{userId}/{name}/{author}")
    public void addBookThatDoesntExist(@PathVariable String name,@PathVariable String author,
                                       @PathVariable String title,@PathVariable  int userId){
        auctionService.addAuctionForNewBook(name, author, title, userId);
    }

    @PutMapping("/SoldItem/{auctionId}")
    public void auctionEnded(@PathVariable int auctionId){
        auctionService.itemSold(auctionId);
    }

    @DeleteMapping("/DeleteBid/{auctionId}/{bidId}")
    public void deleteBid(@PathVariable  int auctionId,@PathVariable int bidId){
        auctionService.removeBidById(auctionId,bidId);
    }

    @GetMapping("/ConfirmAuction/{auctionId}")
    public boolean confirmAuction(@PathVariable int auctionId){
        auctionService.confirmAuction(auctionId);
        return true;
    }
}
