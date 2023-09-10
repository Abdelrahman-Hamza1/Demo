package com.demo.books.management;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuctionedBooksRepository extends JpaRepository<AuctionedBook,Integer> {
    List<AuctionedBook> findAllByStatusIs(Status status);
}
