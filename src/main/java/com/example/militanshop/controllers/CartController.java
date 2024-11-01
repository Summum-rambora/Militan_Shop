package com.example.militanshop.controllers;

import com.example.militanshop.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CartController {

    private final CartService cartService;
    private final Long defaultCartId = 1L;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String viewCart(Model model) {
        model.addAttribute("cart", cartService.getCart(defaultCartId));
        return "cart";
    }

    @PostMapping("/cart/add/{productId}")
    public String addProductToCart(@PathVariable Long productId) {
        cartService.addProductToCart(defaultCartId, productId);
        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{productId}")
    public String removeProductFromCart(@PathVariable Long productId) {
        cartService.removeProductFromCart(defaultCartId, productId);
        return "redirect:/cart";
    }
}
