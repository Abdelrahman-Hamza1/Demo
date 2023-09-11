package com.demo.authorservice.messaging;


import lombok.Data;

@Data
public class Message {
    String UUID;
    String authorName;
    // could add more details , not the point now.
}
