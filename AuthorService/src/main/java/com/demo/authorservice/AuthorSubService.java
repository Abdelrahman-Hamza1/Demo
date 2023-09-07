package com.demo.authorservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorSubService {
    AuthorRepository authorRepository;

    public void addSubscription(String authorName, String username){
        authorRepository.save(new AuthorSubscription(authorName,username,false));
    }

}
