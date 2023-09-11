package com.demo.books.models;

import jakarta.persistence.*;
import lombok.*;

@ToString@NoArgsConstructor@Getter@Setter@EqualsAndHashCode(callSuper = true)
@Entity
@Table
public class Book extends AbstractBook {
    int price;
    int quantity;

    public Book(String name, String author, int price, int quantity) {
        super(name,author);
        this.price = price;
        this.quantity = quantity;
    }


    public void decrementQuantity(){
        this.quantity--;
    }
    public void incrementQuantity(){
        this.quantity++;
    }
}
