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


    /*
    So far, no end-points require checking for username as this is just library related books.
    there is a need for authorization as there are some functionalities that should only be
    available for admins.


    Access token is included in the Header under "Authorization"
    It's structure is : Bearer(space)token. -> split at (space)
    the token consists of three parts separated by a (.) -> we need the body hence get(1).
     */

    @GetMapping("/TestKeyCloak")
    public Object testKeyCloak(@RequestHeader Map<String, String> headers){
        // BY Default -> Username in keycloak is unique -> we can extract it from JWT to verify.
        // This method can present many details.
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String x  =  new String(decoder.decode(headers.get("authorization")
                .split(" ")[1].split("\\.")[1]));
        System.out.println(x);
        try {
            JSONObject jsonObject = new JSONObject(x);
            return  jsonObject.toString();//.getString("preferred_username");

        }catch (Exception e){
            System.out.println(x);
        }
        return x;
    }


    @GetMapping("/IsValid/{bookId}")
    public boolean isValidBookId (@PathVariable int bookId){
        return bookService.isValid(bookId);
    }

    @GetMapping("/Add/{name}/{author}/{price}/{quantity}")
    public Book addBook(@PathVariable String name,@PathVariable String author,
                        @PathVariable int price,@PathVariable int quantity){
        return bookService.addBook(new Book (name,  author,  price,  quantity));
    }

    @GetMapping("/Update/{id}/{name}/{author}/{price}/{quantity}")  // EXPECTING EVERYTHING TO BE INCLUDED IN THE BODY
    public void updateBook(@PathVariable int id, @PathVariable String name,@PathVariable String author,
                           @PathVariable int price,@PathVariable int quantity){
         bookService.updateBook(id, name,  author,  price,  quantity);
    }

    @GetMapping("/GetBooks")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("/GetBooks/Like/{name}")
    public List<Book> getBooksContainingName(@PathVariable String name){
        return bookService.getBookContainingName(name);
    }



    @GetMapping("/GetBookById/{bookId}")
    public Book getBookById(@PathVariable int bookId){
        return bookService.getBookById(bookId);
    }

    @GetMapping("/Delete/{bookId}")
    public void delete(@PathVariable int bookId){
        bookService.deleteBook(bookId);
    }

    @GetMapping("/ConfirmBook/{bookId}/{auctionId}") // MUST BE ADMIN
    public void confirmBook(@PathVariable int bookId, @PathVariable int auctionId){
        bookService.confirmAuctionedBook(bookId,auctionId,Status.ACCEPTED);
    }

    @GetMapping("/GetPendingBooks")
    public List<AuctionedBook> getAllAuctionedBooks(){
        return bookService.getAllAuctionedBooks();
    }

}
