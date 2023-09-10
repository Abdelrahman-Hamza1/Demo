package com.demo.books.management;

import com.demo.books.messaging.AuthorMessage;
import com.demo.books.messaging.RabbitMQConfiguration;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BookService {

    BookRepository bookRepository;
    AuctionedBooksRepository auctionedBooksRepository;
    RestTemplate restTemplate;
    RabbitTemplate template;

    public Book addBook(Book book){
        // Also send message to a queue which user service will consume and notify user accordingly.
        AuthorMessage message = new AuthorMessage();

        message.setUUID(UUID.randomUUID().toString());
        message.setAuthorName(book.getAuthor());

        template.convertAndSend(RabbitMQConfiguration.EXCHANGE,
                RabbitMQConfiguration.ROUTING_KEY_THREE, message);

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

    public void updateBook( int id,  String name,  String author,int price,  int quantity){
        Book b = bookRepository.findById(id).get();
        b.setPrice(price);
        b.setQuantity(quantity);
        b.setName(name);
        b.setAuthor(author);

        bookRepository.save(b);

    }

    public void deleteBook(int bookId){
        bookRepository.deleteById(bookId);
    }

    public boolean isValid(int bookId){
        return bookRepository.findById(bookId).isPresent();
    }

    public List<AuctionedBook> getAllAuctionedBooks(){
        return auctionedBooksRepository.findAllByStatusIs(Status.PENDING);
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
