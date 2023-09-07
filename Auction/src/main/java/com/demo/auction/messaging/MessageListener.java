package com.demo.auction.messaging;

import com.demo.auction.auctions.AuctionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageListener {

    @Autowired
    AuctionService auctionService;

//    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_TWO)
//    public void listener(Message message) {
//        System.out.println("\n\n\n\n" + message);
//
//        String [] messageBody = message.getMessage().split("-");
//        if(messageBody[0].equalsIgnoreCase("yes")){
//            auctionService.confirmAuction(Integer.parseInt(messageBody[1]));
//        }
//    }
}

