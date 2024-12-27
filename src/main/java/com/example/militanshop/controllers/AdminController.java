package com.example.militanshop.controllers;

import com.example.militanshop.models.Role;
import com.example.militanshop.models.User;
import com.example.militanshop.repositories.UserRep;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminController {

    private final UserRep userRepository;

    public AdminController(UserRep userRepository) {
        this.userRepository = userRepository;
    }

    // Страница для отображения всех пользователей с возможностью поиска
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @GetMapping("/admin/users")
    public String getUsers(@RequestParam(name = "search", required = false) String search, Model model) {
        List<User> users;
        if (search != null && !search.isEmpty()) {
            users = (List<User>) userRepository.findByUsername(search);
        }
        else {
            users = userRepository.findAll();  // Выводим всех пользователей
        }
        model.addAttribute("users", users);
        model.addAttribute("search", search);
        return "admin/users";  // Шаблон с таблицей пользователей
    }

    // Назначение роли пользователю
    @PreAuthorize("hasAuthority('SUPER_ADMIN')")
    @PostMapping("/assignRole")
    public String assignRole(@RequestParam Long userId, @RequestParam Role role) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {

            user.getRoles().clear();


            user.getRoles().add(role);


            userRepository.save(user);
            return "redirect:/admin/users";
        }
        return "redirect:/admin/users?error";
    }
}
