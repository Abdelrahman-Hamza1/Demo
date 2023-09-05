package com.demo.books;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/Book")
@AllArgsConstructor
public class BookController {

    BookService bookService;


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

    @GetMapping("/GetBookById/{bookId}")
    public Book getBookById(@PathVariable int bookId){
        return bookService.getBookById(bookId);
    }

    @DeleteMapping("/Delete/{bookId}")
    public void delete(@PathVariable int bookId){
        bookService.deleteBook(bookId);
    }

}
