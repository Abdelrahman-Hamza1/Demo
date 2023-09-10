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

            // This message handler is activated every-time a message is written to the queue
            // Currently, only book service writes to the queue
            // We can let auction service write to the same queue -> also notify
            // We could add book Id or auction Id to the message !
            List<AuthorSubscription> subs = authorRepository
                    .findAllByAuthorName(message.getAuthorName());

            for (AuthorSubscription sub: subs) {
                // Assume we send message(notification here)
                System.out.println(sub.getUsername() + " " + sub.getUserEmail());
            }

    }
}


