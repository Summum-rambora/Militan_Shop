package com.example.militanshop.services;

import com.example.militanshop.models.Cart;
import com.example.militanshop.models.Product;
import com.example.militanshop.repositories.CartRepository;
import com.example.militanshop.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartService(CartRepository cartRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public Cart getCart(Long cartId) {
        return cartRepository.findById(cartId).orElseGet(() -> cartRepository.save(new Cart()));
    }

    public void addProductToCart(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        Optional<Product> product = productRepository.findById(productId);
        product.ifPresent(cart::addProduct);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Long cartId, Long productId) {
        Cart cart = getCart(cartId);
        cart.getProducts().removeIf(product -> product.getId() == productId);
        cartRepository.save(cart);
    }

}
