package com.demo.authorservice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Author")
public class AuthorController {

    AuthorSubService authorService;


}
