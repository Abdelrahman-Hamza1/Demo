package com.demo.wishlist;

import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.util.Base64;
import java.util.List;
import java.util.Map;


@AllArgsConstructor
@RestController
@RequestMapping("/Wishlist")
public class WishlistController {

    WishlistService wishlistService;

    @GetMapping("/Create/{title}")
    public void createWishlist(@PathVariable String title,@RequestHeader Map<String, String> headers){
        wishlistService.createWishlist(title,getUsername(headers));
    }

    @GetMapping("/GetAll")
    public List<Wishlist> getAllWishLists(){
        return wishlistService.getAllWishLists();
    }

    @GetMapping("/GetById/{id}")
    public Wishlist getById(@PathVariable int id){
        return wishlistService.getById(id);
    }

    @GetMapping("/GetByUser")
    public List<Wishlist> getByUserName(@RequestHeader Map<String, String> headers){
        return wishlistService.findByUserId(getUsername(headers));
    }

    @GetMapping("/AddBook/{bookId}/{wishListId}")
    public Wishlist addBook(@PathVariable  int bookId,@PathVariable int wishListId,
                            @RequestHeader Map<String, String> headers){
        return wishlistService.addBook(bookId,wishListId, getUsername(headers));
    }

    @GetMapping("/DeleteBook/{bookId}/{wishListId}")
    public void removeBook(@PathVariable  int bookId,@PathVariable int wishListId,
                           @RequestHeader Map<String,String> headers){
         wishlistService.removeBook(bookId,wishListId, getUsername(headers));
    }

    // Get username & verify ownership.
    @GetMapping("/Delete/{wishListId}")
    public void removeWishList(@PathVariable int wishListId,@RequestHeader Map<String,String> headers){
        wishlistService.removeWishlist(wishListId,getUsername(headers));
    }

    @GetMapping("/UpdateTitle/{wishListId}/{title}")
    public void updateWishListTitle(@PathVariable String title, @PathVariable int wishListId,
                                    @RequestHeader Map<String,String> headers){
        wishlistService.updateTitle(title,wishListId,getUsername(headers));
    }

    public String getUsername(Map<String, String> headers){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String x  =  new String(decoder.decode(headers.get("authorization")
                .split(" ")[1].split("\\.")[1]));
        try {
            JSONObject jsonObject = new JSONObject(x);
            return  jsonObject.getString("preferred_username");

        }catch (Exception e){
            return "";
        }
    }
}
