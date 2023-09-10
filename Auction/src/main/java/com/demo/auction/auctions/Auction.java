package com.demo.auction.auctions;

import com.demo.auction.bid.Bid;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;

@Getter@Setter@ToString@EqualsAndHashCode@NoArgsConstructor
@Entity@Table
public class Auction {
    @Id
    @SequenceGenerator(
            name = "auction_sequence",
            sequenceName = "auction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "auction_sequence"
    )
    int id;
    String title;
    int bookId;
    String username;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Bid> bids;
    AuctionStatus status;
    int buyerId = -1;
    Trust trust = Trust.UNKNOWN;

    public Auction(String title, int bookId, String username, List<Bid> bids, AuctionStatus status) {
        this.title = title;
        this.bookId = bookId;
        this.username = username;
        this.bids = bids;
        this.status = status;
    }

    public void addBid(Bid bid){
        this.bids.add(bid);
    }

    public void removeBidById(int bidId){
        bids = bids.stream()
                .filter(b -> b.getId() != bidId)
                .collect(Collectors.toList());
    }
}
