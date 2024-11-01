package com.example.militanshop.controllers;


import com.example.militanshop.models.Product;
import com.example.militanshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }



    @GetMapping("/")
    public String ShowLoginPage(){
        return "redirect:/login";
    }



    @GetMapping("/main")
    public String viewHomePage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "main";
    }

    @GetMapping("/addProduct")
    public String addProductPage(Model model) {
        model.addAttribute("product", new Product());
        return "addProduct";
    }

    @PostMapping("/saveProduct")
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/main";
    }



    @GetMapping("/product/{id}")
    public String viewProductPage(Model model, @PathVariable Long id) {
        Product product = productService.findProductById(id);
        model.addAttribute("product", product);
        return "productPage";
    }


}
