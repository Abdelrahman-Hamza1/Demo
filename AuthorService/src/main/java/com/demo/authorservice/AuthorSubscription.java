package com.demo.authorservice;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Entity@Table@NoArgsConstructor
public class AuthorSubscription {
    @Id
    @SequenceGenerator(
            name = "author_sequence",
            sequenceName = "author_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "author_sequence"
    )
    int id;
    String authorName;
    String username;
    String userEmail;
//    Boolean isDeleted;

    public AuthorSubscription(String authorName, String username, String userEmail) {
        this.authorName = authorName;
        this.username = username;
        this.userEmail = userEmail;
    }
}
