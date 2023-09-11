package com.demo.books.messaging;

import com.demo.books.models.AuctionedBook;
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
