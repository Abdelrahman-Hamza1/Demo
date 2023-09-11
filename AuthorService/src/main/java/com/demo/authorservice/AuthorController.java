package com.demo.authorservice;


import lombok.AllArgsConstructor;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/Author")
@AllArgsConstructor
public class AuthorController {

    AuthorSubService authorService;

    @GetMapping("/GetAll")
    public List<AuthorSubscription> getAll(){
        return authorService.getAll();
    }

    @GetMapping("/AddSub/{authorName}")
    public void addSub(@PathVariable String authorName, @RequestHeader Map<String, String> headers){
        authorService.addSubscription(authorName,getUsername(headers), getUserEmail(headers));
    }

    @GetMapping("/DeleteSub/{authorName}")
    public void deleteSub(@PathVariable String authorName, @RequestHeader Map<String, String> headers){
        authorService.deleteSubscription(authorName,getUsername(headers));
    }

    /*
    POSSIBLE UPDATE:
        - ADD NEW ENDPOINT THAT IS LIMITED TO ADMINS THAT ALLOWS THEM TO ADD/REMOVE FREELY.
     */

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
    public String getUserEmail(Map<String, String> headers){
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String x  =  new String(decoder.decode(headers.get("authorization")
                .split(" ")[1].split("\\.")[1]));
        try {
            JSONObject jsonObject = new JSONObject(x);
            return  jsonObject.getString("email");

        }catch (Exception e){
            return "";
        }
    }

}
