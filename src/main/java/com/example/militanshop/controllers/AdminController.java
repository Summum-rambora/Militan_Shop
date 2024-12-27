package com.example.militanshop.controllers;

import com.example.militanshop.models.Role;
import com.example.militanshop.models.User;
import com.example.militanshop.repositories.UserRep;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;


@Controller
public class AdminController {
    private final UserRep userRepository;

    public AdminController(UserRep userRepository) {
        this.userRepository = userRepository;
    }

    @Secured("ROLE_SUPER_ADMIN")
    @PostMapping("/assignRole")
    public String assignRole(@RequestParam String username, @RequestParam Role role) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            user.getRoles().add(role);
            userRepository.save(user);
            return "Role " + role + " assigned to user " + username;
        } else {
            return "User not found!";
        }
    }

}


