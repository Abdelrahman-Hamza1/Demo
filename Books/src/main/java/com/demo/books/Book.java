package com.demo.books;

import jakarta.persistence.*;
import lombok.*;

@ToString@NoArgsConstructor@Getter@Setter
@Entity
@Table
public class Book {
    @Id
    @SequenceGenerator(
            name = "book_sequence",
            sequenceName = "book_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_sequence"
    )
    int id;
    String name;
    String author;
    int price;
    int quantity;

    public Book(String author, int price, int quantity) {
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public void decrementQuantity(){
        this.quantity--;
    }
}
