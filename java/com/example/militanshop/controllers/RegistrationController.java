package com.example.militanshop.controllers;


import com.example.militanshop.models.DTO.Mappers.UserMapper;
import com.example.militanshop.models.DTO.UserDTO;
import com.example.militanshop.models.Role;
import com.example.militanshop.models.User;
import com.example.militanshop.repositories.UserRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.militanshop.models.DTO.RegistrationDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
@Controller
public class RegistrationController {


    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UserRep userRep;


    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(RegistrationDTO registrationDTO, Map<String, Object> model) {
        User userFromDb =  userRep.findByUsername(registrationDTO.getUsername());


        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        User user = UserMapper.toEntity(registrationDTO);


        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRep.save(user);
        return "redirect:/login";
    }



}
