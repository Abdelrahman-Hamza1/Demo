package com.demo.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBacks {

    @GetMapping("/Handle-Failure")
    Mono<String> getBooksFallback() {
        return Mono.just("Sorry brother, this service is currently down! " +
                "Please try again later");
    }
}
