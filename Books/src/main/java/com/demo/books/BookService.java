package com.demo.books;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@AllArgsConstructor
@Service
public class BookService {

    BookRepository bookRepository;

    public Book addBook(Book book){
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

}
