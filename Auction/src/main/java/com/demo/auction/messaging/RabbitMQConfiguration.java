package com.demo.auction.messaging;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfiguration {

    public static final String QUEUE_ONE = "AucToBooks";
    public static final String QUEUE_TWO = "BookToAuc";
    public static final String EXCHANGE = "message_exchange";
    public static final String ROUTING_KEY_ONE = "message_routingKey_ONE";
    public static final String ROUTING_KEY_TWO = "message_routingKey_TWO";

    @Bean
    Queue queue1() {
        return new Queue(QUEUE_ONE,false);
    }

    @Bean
    Queue queue2() {
        return new Queue(QUEUE_TWO, false);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    Binding binding1(TopicExchange exchange) {
        return BindingBuilder.bind(queue1()).to(exchange).with(ROUTING_KEY_ONE);
    }

    @Bean
    Binding binding2(TopicExchange exchange) {
        return BindingBuilder.bind(queue2()).to(exchange).with(ROUTING_KEY_TWO);
    }

    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }
}
