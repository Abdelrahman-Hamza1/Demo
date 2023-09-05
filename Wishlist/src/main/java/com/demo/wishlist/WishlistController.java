package com.demo.wishlist;


import jakarta.ws.rs.Path;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/Wishlist")
public class WishlistController {

    WishlistService wishlistService;

    @PostMapping("/Create/{title}/{userId}")
    public void createWishlist(@PathVariable String title,@PathVariable int userId){
        wishlistService.createWishlist(title,userId);
    }

    @GetMapping("/GetAll")
    public List<Wishlist> getAllWishLists(){
        return wishlistService.getAllWishLists();
    }

    @GetMapping("/GetById/{id}")
    public Wishlist getById(@PathVariable int id){
        return wishlistService.getById(id);
    }

    @GetMapping("/GetByUserId/{userId}")
    public List<Wishlist> getByUserId(@PathVariable  int userId){
        return wishlistService.findByUserId(userId);
    }

    @PostMapping("/AddBook/{bookId}/{wishListId}")
    public Wishlist addBook(@PathVariable  int bookId,@PathVariable int wishListId){
        return wishlistService.addBook(bookId,wishListId);
    }

    @DeleteMapping("/DeleteBook/{bookId}/{wishListId}")
    public void removeBook(@PathVariable  int bookId,@PathVariable int wishListId){
         wishlistService.removeBook(bookId,wishListId);
    }

    @DeleteMapping("/Delete/{wishListId}")
    public void removeWishList(@PathVariable int wishListId){
        wishlistService.removeWishlist(wishListId);
    }

    @PutMapping("/UpdateTitle/{wishListId}/{title}")
    public void updateWishListTitle(@PathVariable String title, @PathVariable int wishListId){
        wishlistService.updateTitle(title,wishListId);
    }
}
