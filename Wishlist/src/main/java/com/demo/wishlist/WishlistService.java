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
    private RestTemplate restTemplate;

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
            if(w.getUsername().equals(username) || isAdmin(username)){
                w.addBook(bookId);
                wishlistRepository.save(w);
                return w;
            }
        }
        return null;
    }

    public void removeBook(int bookId, int wishListId, String username){
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishListId);
        if(optionalWishlist.isPresent()){
            Wishlist w = optionalWishlist.get();
            if(w.getUsername().equals(username) || isAdmin(username)){
                w.removeBook(bookId);
                wishlistRepository.save(w);
            }
        }
    }

    public void removeWishlist(int wishListId, String username){
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishListId);
        if(optionalWishlist.isPresent()){
            Wishlist w = optionalWishlist.get();
            if(w.getUsername().equals(username) || isAdmin(username)){
                wishlistRepository.deleteById(wishListId);
            }
        }
    }

    public void updateTitle(String title, int wishListId, String username) {
        Optional<Wishlist> optionalWishlist = wishlistRepository.findById(wishListId);
        if(optionalWishlist.isPresent()){
            Wishlist w = optionalWishlist.get();
            if(w.getUsername().equals(username) || isAdmin(username)){
                w.setTitle(title);
                wishlistRepository.save(w);
            }
        }
    }
    public boolean isAdmin(String username){
        return Boolean.TRUE.equals(restTemplate.getForObject(
                "lb://AUTHORIZATION/Authorization/IsAdmin/" + username
                , Boolean.class));
    }
}
