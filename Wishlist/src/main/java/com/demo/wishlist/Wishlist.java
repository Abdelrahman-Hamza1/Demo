package com.demo.wishlist;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.stream.Collectors;


@Getter@Setter@ToString@NoArgsConstructor@EqualsAndHashCode
@Entity@Table
public class Wishlist {
    @Id
    @SequenceGenerator(
            name = "auction_sequence",
            sequenceName = "auction_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "auction_sequence"
    )
    int id;
    String title;
    String username;
    @ElementCollection@CollectionTable
    List<Integer> bookList;


    public Wishlist(String title, String username, List<Integer> bookList) {
        this.title = title;
        this.username = username;
        this.bookList = bookList;
    }

    void addBook(int bookId){
        this.bookList.add(bookId);
    }

    void removeBook(int bookId){
        this.bookList = this.bookList.stream()
                .filter(b -> b != bookId).collect(Collectors.toList());
    }
}
