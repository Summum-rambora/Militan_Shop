package com.example.militanshop.controllers;


import com.example.militanshop.models.DTO.Mappers.ProductMapper;
import com.example.militanshop.models.DTO.ProductDTO;
import com.example.militanshop.models.Feedback;
import com.example.militanshop.models.Product;
import com.example.militanshop.services.FeedbackService;
import com.example.militanshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @Autowired
    private FeedbackService feedbackService;


    @GetMapping("/")
    public String ShowLoginPage(){
        return "redirect:/login";
    }



    @GetMapping("/main")
    public String viewHomePage(Model model) {

        List<ProductDTO> products = productService.getAllProducts()
                .stream()
                .map(product -> new ProductDTO(product.getId(), product.getName(),product.getName(), product.getPrice()))
                .collect(Collectors.toList());
        model.addAttribute("products", products);
        return "main";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addProduct")
    public String addProductPage(Model model) {
        model.addAttribute("product", new ProductDTO());
        return "addProduct";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveProduct")
    public String saveProduct(ProductDTO productDTO) {
        Product product = ProductMapper.toEntity(productDTO);
        productService.saveProduct(product);
        return "redirect:/main";
    }



    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        List<Feedback> feedbackList = feedbackService.getFeedbackByProduct(product);


        ProductDTO productDTO = ProductMapper.toDTO(product);

        model.addAttribute("product", productDTO);
        model.addAttribute("feedbackList", feedbackList);

        return "productPage";
    }


}
