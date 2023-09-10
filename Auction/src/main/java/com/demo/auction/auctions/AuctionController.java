package com.demo.auction.auctions;


import com.demo.auction.bid.Bid;
import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Auction")
@AllArgsConstructor
public class AuctionController {

    AuctionService auctionService;

    @GetMapping("/GetAll")
    public List<Auction> getAll(){
        return auctionService.getAllAuctions();
    }

    @GetMapping("/CreateAuction/{title}/{bookId}")
    public void createAuction(@PathVariable  String title
            ,@PathVariable int bookId
            ,@RequestHeader Map<String, String> headers){
        auctionService.createAuction(title,bookId,getUsername(headers));
    }

    @GetMapping("/GetAuctions/{title}")
    public List<Auction> getAuctionsByTitleContaining(@PathVariable  String title){
        return auctionService.getAuctionsByTitleContaining(title);
    }

    @GetMapping("/AddBid/{auctionId}/{amount}/{comment}")
    public void addBidOnAuction(@PathVariable int auctionId,@PathVariable double amount,
                                @PathVariable String comment, @RequestHeader Map<String, String> headers){
        auctionService.addBidOnAuction(auctionId, new Bid(amount,getUsername(headers),
                "today's date", comment));
    }

    @GetMapping("/AddNewBookAuction/{title}/{name}/{author}")
    public void addBookThatDoesntExist(@PathVariable String name,@PathVariable String author,
                                       @PathVariable String title,@RequestHeader Map<String, String> headers){
        auctionService.addAuctionForNewBook(name, author, title, getUsername(headers));
    }

    @GetMapping("/SoldItem/{auctionId}")
    public void auctionEnded(@PathVariable int auctionId){
        auctionService.itemSold(auctionId);
    }

    @GetMapping("/DeleteBid/{auctionId}/{bidId}")
    public void deleteBid(@PathVariable  int auctionId,@PathVariable int bidId){
        auctionService.removeBidById(auctionId,bidId);
    }

    @GetMapping("/ConfirmAuction/{auctionId}")
    public boolean confirmAuction(@PathVariable int auctionId){
        auctionService.confirmAuction(auctionId);
        return true;
    }

    // Takes the @RequestHeader as an input
    public String getUsername(Map<String, String> headers){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String x  =  new String(decoder.decode(headers.get("authorization")
                .split(" ")[1].split("\\.")[1]));
        System.out.println(x);
        try {
            JSONObject jsonObject = new JSONObject(x);
            return  jsonObject.getString("preferred_username");

        }catch (Exception e){
            return "";
        }
    }
}
