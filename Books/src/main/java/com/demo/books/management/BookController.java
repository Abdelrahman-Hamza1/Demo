package com.demo.books.management;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.List;

@RestController
@RequestMapping("/Book")
@AllArgsConstructor
public class BookController {

    BookService bookService;
    RabbitTemplate template;



    @GetMapping("/TestTwo")
    public Object testnumberTwo(){
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @GetMapping("/TestKeyCloak")
    public String testKeyCloak(@RequestHeader Map<String, String> headers){
        // BY Default -> Username in keycloak is unique -> we can extract it from JWT to verify.
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String x  =  new String(decoder.decode(headers.get("authorization").split(" ")[1].split("\\.")[1]));
        try {
            JSONObject jsonObject = new JSONObject(x);
            return jsonObject.getString("preferred_username");
        }catch (Exception e){
            System.out.println(x);
        }
        return x;
    }


    @GetMapping("/IsValid/{bookId}")
    public boolean isValidBookId (@PathVariable int bookId){
        return bookService.isValid(bookId);
    }

    @PostMapping("/Add")
    public Book addBook(@RequestBody Book book){
        return bookService.addBook(book);
    }

    @PutMapping("/Update")  // EXPECTING EVERYTHING TO BE INCLUDED IN THE BODY
    public void updateBook(@RequestBody Book book){
         bookService.updateBook(book);
    }

    @GetMapping("/GetBooks")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/GetBooks/Like/{name}")
    public List<Book> getBooksContainingName(@PathVariable String name){
        return bookService.getBookContainingName(name);
    }

    @PostMapping("/ConfirmRequest/{auctionId}")
    public void confirmBook(@PathVariable  int auctionId){
//        Message message = new Message();
//        message.setMessage("yes-"+auctionId); // "yes" or "no"
//        message.setMessageId(UUID.randomUUID().toString());
//        template.convertAndSend(RabbitMQConfiguration.EXCHANGE,
//                RabbitMQConfiguration.ROUTING_KEY_TWO, message);
    }

    @GetMapping("/GetBookById/{bookId}")
    public Book getBookById(@PathVariable int bookId){
        return bookService.getBookById(bookId);
    }

    @DeleteMapping("/Delete/{bookId}")
    public void delete(@PathVariable int bookId){
        bookService.deleteBook(bookId);
    }

    @GetMapping("/ConfirmBook/{bookId}/{auctionId}")
    public void confirmBook(@PathVariable int bookId, @PathVariable int auctionId){
        bookService.confirmAuctionedBook(bookId,auctionId,Status.ACCEPTED);
    }
    @GetMapping("/GetPendingBooks")
    public List<AuctionedBook> getAllAuctionedBooks(){
        return bookService.getAllAuctionedBooks();
    }

}
