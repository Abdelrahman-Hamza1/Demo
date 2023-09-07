package com.demo.books.management;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuctionedBooksRepository extends JpaRepository<AuctionedBook,Integer> {
}
