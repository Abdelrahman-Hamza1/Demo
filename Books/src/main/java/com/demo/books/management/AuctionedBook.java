package com.demo.books.management;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity@Table
@ToString(callSuper = true)
public class AuctionedBook extends AbstractBook{
    Status status;
    int auctionId;
}
