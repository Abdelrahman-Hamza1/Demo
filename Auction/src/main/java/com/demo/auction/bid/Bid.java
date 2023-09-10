package com.demo.auction.bid;


import jakarta.persistence.*;
import lombok.*;

@Getter@Setter@ToString@EqualsAndHashCode@NoArgsConstructor
@Entity@Table
public class Bid {
    @Id
    @SequenceGenerator(
            name = "bid_sequence",
            sequenceName = "bid_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bid_sequence"
    )
    int id;
    double amount;
    String username;
    String date; // CHANGE TO DATE OBJECT LATER -> MATCH WITH MYSQL DATE
    String comment;

    public Bid(double amount, String username, String date, String comment) {
        this.amount = amount;
        this.username = username;
        this.date = date;
        this.comment = comment;
    }
}
