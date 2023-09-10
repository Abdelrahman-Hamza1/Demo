package com.demo.books;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                      auth.anyRequest().permitAll();
//                    auth.requestMatchers("/SystemAdmin/**").hasRole("ADMIN");
//                    auth.requestMatchers("/User/**").hasAnyRole("ADMIN", "USER")
                });
        return http.build();
    }

}
