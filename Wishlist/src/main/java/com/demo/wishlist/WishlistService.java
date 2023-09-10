package com.demo.wishlist;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class WishlistService {

    WishlistRepository wishlistRepository;


    public void createWishlist(String title, String username){
        wishlistRepository.save(new Wishlist(title,username,new ArrayList<>()));
    }

    public List<Wishlist> getAllWishLists(){
        return wishlistRepository.findAll();
    }

    public Wishlist getById(int Id){
        return wishlistRepository.findById(Id).get();
    }

    public List<Wishlist> findByUserId(String username){
        return  wishlistRepository.findAllByUsername(username);
    }

    public Wishlist addBook(int bookId, int wishlistId){
        Wishlist w = wishlistRepository.findById(wishlistId).get();
        w.addBook(bookId);
        wishlistRepository.save(w);
        return w;
    }

    public void removeBook(int bookId, int wishListId){
        Wishlist w = wishlistRepository.findById(wishListId).get();
        w.removeBook(bookId);
        wishlistRepository.save(w);
    }

    public void removeWishlist(int wishListId){
        wishlistRepository.deleteById(wishListId);
    }

    public void updateTitle(String title, int wishListId) {
        Wishlist w = wishlistRepository.findById(wishListId).get();
        w.setTitle(title);
        wishlistRepository.save(w);
    }
}
