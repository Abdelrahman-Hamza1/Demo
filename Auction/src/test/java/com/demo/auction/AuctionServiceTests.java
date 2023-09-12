package com.demo.auction;

import com.demo.auction.auctions.AuctionRepository;
import com.demo.auction.auctions.AuctionService;
import com.demo.auction.bid.Bid;
import com.demo.auction.bid.BidRepository;
import com.demo.auction.models.Auction;
import com.demo.auction.models.AuctionStatus;
import com.demo.auction.models.Communications;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuctionServiceTests {

    @Mock AuctionRepository auctionRepository;
    @Mock BidRepository bidRepository;
    @Mock RabbitTemplate template;
    @Mock Communications communications;

    @InjectMocks
    AuctionService auctionService;

    @Test
    public void testBidAddition(){
        // If PENDING or SOLD should be able to place bid
        Auction auction = new Auction("Auc title", 1, "user"
                    ,new ArrayList<>(),AuctionStatus.PENDING);
        assertNull(auctionService.addBidOnAuction(auction.getId(), null));
        auction.setStatus(AuctionStatus.SOLD);
        assertNull(auctionService.addBidOnAuction(auction.getId(), null));

        // if ongoing -> must add bid || make repo return the auction object
        auction.setStatus(AuctionStatus.ONGOING);
        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));
        when(auctionRepository.save(auction)).thenReturn(auction);

        // Bid x will be added manually to an auction, and added to the auction above via service
        // since status is ongoing it should add it and both auctions should have the same Bids list.
        Bid bid = new Bid(19,"user","","");
        Auction auc = new Auction();
        auc.addBid(bid);
        assertEquals(
                auctionService.addBidOnAuction(auction.getId(),bid).getBids(), auction.getBids());
    }

    @Test
    public void testItemSold(){
        /*
        if neither admin nor owner -> null
        if admin and not owner -> go through.
        if owner and not admin -> go through.
        go through: status == SOLD.
         */
        assertNull(auctionService.itemSold(0, "jameel"));
        Auction auction = new Auction("Auc title", 1, "user"
                ,new ArrayList<>(),AuctionStatus.ONGOING);
        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));
        when(auctionRepository.save(auction)).thenReturn(auction);

        assertNull(auctionService.itemSold(auction.getId(), "jameel")); // neither nor

        // owner but not admin
        assertEquals(auctionService.itemSold(auction.getId(), "user").getStatus()
                , AuctionStatus.SOLD);

        // admin but not owner
        when(communications.isAdmin("khalil")).thenReturn(true);
        assertEquals(auctionService.itemSold(auction.getId(), "khalil").getStatus()
                , AuctionStatus.SOLD);


    }
    @Test
    public void testBidRemovalById(){
        /*
        Only accepts to remove the bid if the username is the bid owner (or an admin)
        if auction or bid dont exist -> null.

         try bid that exists but doesnt belong to auction
         */

        // Invalid auction id
        assertNull(auctionService.removeBidById(0,0,""));
        Auction auction = new Auction("Auc title", 1, "user"
                ,new ArrayList<>(),AuctionStatus.ONGOING);
        // invalid bid id
        assertNull(auctionService.removeBidById(auction.getId(), 0,""));


        Bid bid = new Bid(0,22.5, "user", "Today", "Sum comment");
        Bid newBid = new Bid(1,33.5, "user", "Today", "Sum comment");
        Bid lastBid = new Bid(2,22,"user", "","");
        auction.addBid(bid);
        auction.addBid(newBid);

        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));
        when(bidRepository.findById(bid.getId())).thenReturn(Optional.of(bid));
        //

        // not admin nor user -> null
        when(communications.isAdmin("jasem")).thenReturn(false);
        assertNull(auctionService.removeBidById(auction.getId(), bid.getId(), "jasem"));

        // is admin
        List<Bid> testBids = new ArrayList<>();
        testBids.add(newBid);
        when(communications.isAdmin("khalil")).thenReturn(true);
        when(auctionRepository.save(auction)).thenReturn(auction);
        assertEquals(testBids,
                auctionService.removeBidById(auction.getId(), bid.getId(),"khalil").getBids());
        // Re add after removal
        auction.addBid(bid);
        List<Bid> testBids2 = new ArrayList<>();
        testBids2.add(bid);
        // Owner
        when(bidRepository.findById(newBid.getId())).thenReturn(Optional.of(newBid));
        assertEquals(testBids2,
                auctionService.removeBidById(auction.getId(), newBid.getId(),"user").getBids());

        when(bidRepository.findById(lastBid.getId())).thenReturn(Optional.of(lastBid));
        assertEquals(auction.getBids(),
                auctionService.removeBidById(auction.getId(), lastBid.getId(),"user").getBids());
    }

    @Test
    public void testBookConfirmation(){
        assertNull(auctionService.confirmAuction(0));

        Auction auction = new Auction("Auc title", 1, "user"
                ,new ArrayList<>(),AuctionStatus.PENDING);

        when(auctionRepository.findById(auction.getId())).thenReturn(Optional.of(auction));
        when(auctionRepository.save(auction)).thenReturn(auction);

        assertEquals(auctionService.confirmAuction(auction.getId()).getStatus()
                , AuctionStatus.ONGOING);
    }
}
