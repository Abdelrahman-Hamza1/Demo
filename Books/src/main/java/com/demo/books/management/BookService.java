package com.demo.books.management;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@AllArgsConstructor
@Service
public class BookService {

    BookRepository bookRepository;
    AuctionedBooksRepository auctionedBooksRepository;
    RestTemplate restTemplate;

    public Book addBook(Book book){
        // Also send message to a queue which user service will consume and notify user accordingly.
        return bookRepository.save(book);
    }

    public List<Book> getBooks(){
        return bookRepository.findAll();
    }

    public List<Book> getBookContainingName( String name){
        return bookRepository.findAllByNameContaining(name);
    }

    public Book getBookById(int id){
        if(bookRepository.findById(id).isPresent()){
            return bookRepository.findById(id).get();
        }
        return null;
    }

    public void updateBook(Book book){
        bookRepository.save(book);
    }

    public void deleteBook(int bookId){
        bookRepository.deleteById(bookId);
    }

    public boolean isValid(int bookId){
        return bookRepository.findById(bookId).isPresent();
    }

    public List<AuctionedBook> getAllAuctionedBooks(){
        return auctionedBooksRepository.findAll();
    }

    public AuctionedBook findAuctionedBookById(int id){
        return auctionedBooksRepository.findById(id).get();
    }

    public void confirmAuctionedBook(int bookId, int auctionId, Status status){
        AuctionedBook book = auctionedBooksRepository.findById(bookId).get();
        book.setStatus(status);

        auctionedBooksRepository.save(book);

        // API call to auction to confirm it
        restTemplate.getForObject("http://AUCTION/Auction/ConfirmAuction/" + auctionId , Boolean.class);

    }

}
