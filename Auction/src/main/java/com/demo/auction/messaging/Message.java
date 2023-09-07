package com.demo.auction.messaging;

import com.demo.auction.books.AuctionedBook;
import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String messageId;
    private AuctionedBook book;
}
