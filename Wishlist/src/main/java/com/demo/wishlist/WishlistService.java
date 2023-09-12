package com.demo.wishlist;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WishlistService {

    WishlistRepository wishlistRepository;
    Communications communications;

    // Add checks for administration and username.


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

    public Wishlist addBook(int bookId, int wishlistId,String username){
        // CHECK BOOK ID FOR VALID ??
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishlistId);
        if(optionalWishlist.isPresent()){
            Wishlist w = optionalWishlist.get();
            if(w.getUsername().equals(username) || communications.isAdmin(username)){
                w.addBook(bookId);
                return wishlistRepository.save(w);
            }
        }
        return null;
    }

    public boolean removeBook(int bookId, int wishListId, String username){
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishListId);
        if(optionalWishlist.isPresent()){
            Wishlist w = optionalWishlist.get();
            if(w.getUsername().equals(username) || communications.isAdmin(username)){
                w.removeBook(bookId);
                wishlistRepository.save(w);
                return true;
            }
        }
        return false;
    }

    public boolean removeWishlist(int wishListId, String username){
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishListId);
        if(optionalWishlist.isPresent()){
            Wishlist w = optionalWishlist.get();
            if(w.getUsername().equals(username) || communications.isAdmin(username)){
                wishlistRepository.deleteById(wishListId);
                return true;
            }
        }
        return false;
    }

    public Wishlist updateTitle(String title, int wishListId, String username) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishListId);
        if(optionalWishlist.isPresent()){
            Wishlist w = optionalWishlist.get();
            if(w.getUsername().equals(username) || communications.isAdmin(username)){
                w.setTitle(title);
                return wishlistRepository.save(w);
            }
        }
        return null;
    }

}
