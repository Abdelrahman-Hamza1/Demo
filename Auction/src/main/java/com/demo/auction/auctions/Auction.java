package com.demo.auction.auctions;

import com.demo.auction.Status;
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
    int userId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Bid> bids;
    Status status;

    public Auction(String title, int bookId, int userId, List<Bid> bids, Status status) {
        this.title = title;
        this.bookId = bookId;
        this.userId = userId;
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
