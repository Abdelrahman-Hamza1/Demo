package com.demo.wishlist;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WishlistTests {

    @Mock WishlistRepository wishlistRepository;
    @Mock Communications communications;
    @InjectMocks WishlistService service;
    /*
    Book addition to wishlist
    Book removal from wishlist
    Wishlist removal
    title update.
     */

    @Test
    public void testBookAddition(){
        /*
        PLAN: Create wishlist wish will have an emtpy array, add to it and verify.
        also consider edge cases where username isnt admin/owner etc
         */
        // invalid wishlist
        assertNull(service.addBook(0, 0, ""));
        Wishlist wishlist = new Wishlist("My Wishlist", "user", new ArrayList<>());
        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        // not user nor admin
        assertNull(service.addBook(wishlist.getId(),0,"Ali"));

        // Owner
        assertTrue(service.addBook(5, wishlist.getId(), "user").getBookList().contains(5));

        when(communications.isAdmin("Mohammed")).thenReturn(true);
        // Admin
        assertTrue(service.addBook(3, wishlist.getId(), "Mohammed").getBookList().contains(3));
    }
    @Test
    public void testBookRemoval(){
        assertFalse(service.removeBook(0, 0, ""));

        Wishlist wishlist = new Wishlist("My Wishlist", "user", new ArrayList<>());
        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);

        // not user nor admin
        assertFalse(service.removeBook(wishlist.getId(),0,"Ali"));

        service.addBook(1,wishlist.getId(),"user");
        service.addBook(2,wishlist.getId(),"user");
        // Owner
        service.removeBook(2,wishlist.getId(),"user");
        assertFalse(wishlist.getBookList().contains(2));

        when(communications.isAdmin("Mohammed")).thenReturn(true);
        // Admin
        service.removeBook(1, wishlist.getId(), "Mohammed");
        assertFalse(wishlist.getBookList().contains(1));

    }
    @Test
    public void WishlistRemoval(){
        assertFalse(service.removeWishlist(0,"")); // non-existence wishlist
        Wishlist wishlist = new Wishlist("My Wishlist", "user", new ArrayList<>());
        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        assertFalse(service.removeWishlist(wishlist.getId(),"mohammed")); // false user & isn't admin
        assertTrue(service.removeWishlist(wishlist.getId(),"user")); // real owner

        when(communications.isAdmin("Ali")).thenReturn(true);
        assertTrue(service.removeWishlist(wishlist.getId(),"Ali")); // admin
    }
    @Test
    public void wishlistTitleUpdate(){
        assertFalse(service.removeWishlist(0,"")); // non-existence wishlist
        Wishlist wishlist = new Wishlist("My Wishlist", "user", new ArrayList<>());
        assertNull(service.updateTitle("new", wishlist.getId(), "Obada"));

        when(wishlistRepository.findById(wishlist.getId())).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(wishlist)).thenReturn(wishlist);
        // Owner
        assertEquals("NewWishListTitle",
                service.updateTitle("NewWishListTitle", wishlist.getId(), "user").getTitle());
        // Admin
        when(communications.isAdmin("Ali")).thenReturn(true);
        assertEquals("Latest Title",
                service.updateTitle("Latest Title", wishlist.getId(), "Ali").getTitle());
    }
}
