package com.demo.books.management;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
public class Communications {

     RestTemplate restTemplate;

    public boolean isAdmin(String username){
        return Boolean.TRUE.equals(restTemplate.getForObject(
                "lb://AUTHORIZATION/Authorization/IsAdmin/" + username
                , Boolean.class));
    }
}
