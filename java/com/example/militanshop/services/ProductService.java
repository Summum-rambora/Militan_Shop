package com.example.militanshop.services;

import com.example.militanshop.models.Product;
import com.example.militanshop.repositories.CartRepository;
import com.example.militanshop.repositories.FeedbackRepository;
import com.example.militanshop.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final FeedbackRepository feedbackRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CartRepository cartRepository , FeedbackRepository feedbackRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.feedbackRepository = feedbackRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public void deleteProductById(Long id) {

        feedbackRepository.deleteByProductId(id);


        cartRepository.findAll().forEach(cart -> {
            cart.getProducts().removeIf(product -> Long.valueOf(product.getId()).equals(id));
        });


        productRepository.deleteById(id);
    }
}
