package com.demo.authorservice;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthorSubService {
    AuthorRepository authorRepository;

    public void addSubscription(String authorName, String username,String userEmail){
        authorRepository.save(new AuthorSubscription(authorName,username,userEmail));
    }

    public void deleteSubscription(String authorName, String username){
        authorRepository.deleteAllByAuthorNameAndUsername(authorName,  username);
    }

    public List<AuthorSubscription> getAll(){
        return  authorRepository.findAll();
    }


}
