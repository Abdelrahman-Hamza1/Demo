package com.demo.auction.books;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@AllArgsConstructor@NoArgsConstructor
public class AuctionedBook {
    String name;
    String author;
    Status status;
    int auctionId;
}
