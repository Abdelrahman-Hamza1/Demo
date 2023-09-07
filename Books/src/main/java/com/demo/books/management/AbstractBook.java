package com.demo.books.management;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@EqualsAndHashCode
@MappedSuperclass
public abstract class AbstractBook {

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

    public AbstractBook(String name, String author) {
        this.name = name;
        this.author = author;
    }
}
