package com.demo;

import com.demo.books.management.AuctionedBooksRepository;
import com.demo.books.management.BookRepository;
import com.demo.books.management.BookService;
import com.demo.books.management.Communications;
import com.demo.books.models.Book;
import com.demo.books.models.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock BookRepository bookRepository;
    @Mock AuctionedBooksRepository auctionedBooksRepository;
    @Mock Communications communications;
    @Mock RabbitTemplate template;
    @Mock RestTemplate restTemplate;

    @InjectMocks
    BookService bookService;

    @Test
    public void testBookAddition(){
        Book b = new Book("My Book","Mohammed", 10, 22);
        when(bookRepository.save(b)).thenReturn(b);
        when(communications.isAdmin("ali")).thenReturn(true);
        assertThat(bookService.addBook(b,"ali")).isNotNull();
        assertEquals(bookService.addBook(b,"ali"),b);
    }

    @Test
    public void testNullityWhenNotAdmin(){
        when(communications.isAdmin("user")).thenReturn(false);
        assertThat(bookService.addBook(new Book(), "user")).isNull();
        assertThat(bookService.getAllAuctionedBooks("user")).isNull();
        assertThat(bookService
                .updateBook( 0,  "Name",  "Author",5, 5,"user"))
                .isNull();
        assertFalse(bookService.deleteBook(0, "user"));
        assertFalse(bookService.confirmAuctionedBook(0, Status.ACCEPTED,"user"));
    }

    @Test
    public void testUpdateWithExistingBookAndAdminAccess(){
        when(communications.isAdmin("user")).thenReturn(true);
        Book b = new Book("My Book","Mohammed", 10, 22);

        when(bookRepository.save(b)).thenReturn(b);
        when(bookRepository.findById(b.getId())).thenReturn(Optional.of(b));

        String newName = "aaaa";
        String newAuthor = "bbbb";
        int newPrice = 15;
        int newQuantity = 15;
        Book k = bookService.updateBook( b.getId(),newName,newAuthor,
                                        newPrice,newQuantity,"user");

        assertEquals(newPrice,k.getPrice());
        assertEquals(newQuantity,k.getQuantity());
        assertEquals(newName,k.getName());
        assertEquals(newAuthor,k.getAuthor());
    }

    @Test
    public void testUpdateNonExistentBook(){
        when(communications.isAdmin("user")).thenReturn(true);
        Book b = new Book("My Book","Mohammed", 10, 22);

        when(bookRepository.findById(b.getId())).thenReturn(Optional.empty());

        assertThat(bookService
                .updateBook( 0,  "Name",  "Author",5, 5,"user"))
                .isNull();
    }

    @Test
    public void testingIsValidMethod(){
        Book b = new Book("My Book","Mohammed", 10, 22);
        when(bookRepository.findById(b.getId())).thenReturn(Optional.of(b),Optional.empty());
        assertTrue(bookService.isValid(b.getId()));
        assertFalse(bookService.isValid(b.getId()));
    }

    // Test Confirming book -> it calls external API.
}
