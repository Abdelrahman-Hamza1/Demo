package com.demo.books.messaging;

import com.demo.books.management.AuctionedBooksRepository;
import com.demo.books.models.AuctionedBook;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component@AllArgsConstructor
public class MessageListener {

    AuctionedBooksRepository auctionedBooksRepository;

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_ONE)
    // This service receives messages from AUCTION service regarding books that are listed on auction
    // but are not recognized by the library (aka AuctionedBook) and it adds them to the database
    // waiting for approval from a  n admin
    // -> MUST CHECK IF IT EXISTS & IF IT DOES -> immediately  respond with ok and don't store
    public void listener(Message message) {
        System.out.println("\n\n\n\n" + message);
        AuctionedBook b =  auctionedBooksRepository.save(message.getBook());
        // could send api call to set book id in the auction !
    }
}


