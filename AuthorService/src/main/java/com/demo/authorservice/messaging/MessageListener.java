package com.demo.authorservice.messaging;

import com.demo.authorservice.AuthorRepository;
import com.demo.authorservice.AuthorSubscription;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component@AllArgsConstructor
public class MessageListener {

    AuthorRepository authorRepository;

    @RabbitListener(queues = RabbitMQConfiguration.QUEUE_ONE)
    public void listener(Message message) {
        // Possibility  of letting auctioned books trigger this too !
            List<AuthorSubscription> subs = authorRepository
                    .findAllByAuthorName(message.getAuthorName());

            for (AuthorSubscription sub: subs) {
                // Assume we send message(notification here)
                System.out.println("sending " + sub.getUsername() + " An email on:  "
                        + sub.getUserEmail() + " for " + message.getAuthorName());
            }

    }
}


