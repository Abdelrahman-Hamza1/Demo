package com.demo.authorization;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/Authorization")
@RestController
@AllArgsConstructor
public class MappingController {
    MappingService service;


    @GetMapping("/GetAll")
    public List<Mapping> getAll(){
        return service.getAll();
    }

    @GetMapping("/IsAdmin/{username}")
    public boolean isAdmin(@PathVariable String username){
        return service.isAdmin(username);
    }

    @GetMapping("/AddAdmin/{username}")
    public void addAdmin(@PathVariable String username){
        service.addAdmin(username);
    }

    @GetMapping("/DeleteAdmin/{username}")
    public void removeAdmin(@PathVariable String username){
        service.removeAdmin(username);
    }
}
