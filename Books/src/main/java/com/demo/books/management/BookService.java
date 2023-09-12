package com.demo.books.management;

import com.demo.books.messaging.AuthorMessage;
import com.demo.books.messaging.RabbitMQConfiguration;
import com.demo.books.models.AuctionedBook;
import com.demo.books.models.Book;
import com.demo.books.models.Status;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BookService {

    BookRepository bookRepository;
    AuctionedBooksRepository auctionedBooksRepository;
    Communications communications;
    RabbitTemplate template;
    RestTemplate restTemplate;

    public Book addBook(Book book, String username){
        if(!communications.isAdmin(username)){
            return null;
        }
        // Send book details to the author subscription service through a rabbitMQ Queue.
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

    public List<Book> getBookContainingName(String name){
        return bookRepository.findAllByNameContaining(name);
    }

    public Book getBookById(int id){
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
    }

    public Book updateBook( int id,  String name,  String author,int price,  int quantity,String username){
        if(!communications.isAdmin(username)){
            return null;
        }
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()){
            Book b = book.get();
            b.setPrice(price);
            b.setQuantity(quantity);
            b.setName(name);
            b.setAuthor(author);
            return bookRepository.save(b);
        }
        return null;
    }

    public boolean deleteBook(int bookId,String username){
        if(!communications.isAdmin(username)){
            return false;
        }
        bookRepository.deleteById(bookId);
        return true;
    }

    public boolean isValid(int bookId){
        return bookRepository.findById(bookId).isPresent();
    }

    public List<AuctionedBook> getAllAuctionedBooks(String username){
        if(!communications.isAdmin(username)){
            return null;
        }
        return auctionedBooksRepository.findAllByStatusIs(Status.PENDING);
    }

    public AuctionedBook findAuctionedBookById(int id){
        Optional<AuctionedBook> b = auctionedBooksRepository.findById(id);
        return b.orElse(null);
    }

    public boolean confirmAuctionedBook(int bookId, Status status,String username){
        if(!communications.isAdmin(username)){
            return false;
        }
        Optional<AuctionedBook> b = auctionedBooksRepository.findById(bookId);
        if(b.isPresent()){
            AuctionedBook book = b.get();
            book.setStatus(status);
            auctionedBooksRepository.save(book);
            // COULD FAIL -> HANDLE IT IF THE REQUEST ISN'T SUCCESSFUL.
            restTemplate.getForObject("lb://AUCTION/Auction/ConfirmAuction/" + book.getAuctionId()
                    ,Boolean.class);
            return true;
        }
        return false;
    }
}
