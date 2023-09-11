package com.demo.authorization;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service@AllArgsConstructor
public class MappingService {

    MappingRepository repository;


    public List<Mapping> getAll(){
        return repository.findAll();
    }


    public boolean isAdmin(String username){
        Optional<Mapping> mapping = repository.findByUsername(username);
        if(mapping.isPresent()){
            Mapping m = mapping.get();
            return m.getRole() == Role.ADMIN;
        }
        repository.save(new Mapping(username,Role.USER));
        return false;
    }

    public void addAdmin(String username) {
        Optional<Mapping> mapping = repository.findByUsername(username);
        if(mapping.isPresent()){
            Mapping m = mapping.get();
            m.setRole(Role.ADMIN);
            repository.save(m);
            return;
        }
        repository.save(new Mapping(username,Role.ADMIN));
    }

    public void removeAdmin(String username){
        Optional<Mapping> mapping = repository.findByUsername(username);
        if(mapping.isPresent()){
            Mapping m = mapping.get();
            m.setRole(Role.USER);
            repository.save(m);
            return;
        }
        repository.save(new Mapping(username,Role.USER));
    }
}
