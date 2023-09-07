package com.demo.books.messaging;

import com.demo.books.management.AuctionedBooksRepository;
import com.demo.books.management.BookService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component@AllArgsConstructor
public class MessageListener {

    AuctionedBooksRepository auctionedBooksRepository;

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_ONE)
    public void listener(Message message) {
        System.out.println("\n\n\n\n" + message);
        auctionedBooksRepository.save(message.getBook());
    }
}


