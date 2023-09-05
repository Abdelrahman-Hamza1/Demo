package com.demo.userandauth.controllers;


import com.demo.userandauth.DTO.LoginResponseDTO;
import com.demo.userandauth.DTO.RegistrationDTO;
import com.demo.userandauth.repository.RoleRepository;
import com.demo.userandauth.repository.UserRepository;
import com.demo.userandauth.services.AuthenticationService;
import com.demo.userandauth.userNdb.ApplicationUser;
import com.demo.userandauth.userNdb.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncode;


    @GetMapping("/addRoles")
    public void addRoles() {
        if (roleRepository.findByAuthority("ADMIN").isPresent()) return;
        Role adminRole = roleRepository.save(new Role("ADMIN"));
        roleRepository.save(new Role("USER"));

        Set<Role> roles = new HashSet<>();
        roles.add(adminRole);

        ApplicationUser admin = new ApplicationUser("admin",
                passwordEncode.encode("password"), roles);

        userRepository.save(admin);
    }

    @PostMapping("/register")
    public ApplicationUser registerUser(@RequestBody RegistrationDTO body) {
        return authenticationService.registerUser(body.getUsername(), body.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDTO loginUser(@RequestBody RegistrationDTO body) {
        return authenticationService.loginUser(body.getUsername(), body.getPassword());
    }

}

