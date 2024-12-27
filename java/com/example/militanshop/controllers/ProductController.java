package com.example.militanshop.controllers;


import com.example.militanshop.models.Category;
import com.example.militanshop.models.DTO.Mappers.ProductMapper;
import com.example.militanshop.models.DTO.ProductDTO;
import com.example.militanshop.models.Feedback;
import com.example.militanshop.models.Product;
import com.example.militanshop.services.CategoryService;
import com.example.militanshop.services.FeedbackService;
import com.example.militanshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    private final ProductService productService;

    @Autowired
    private CategoryService categoryService;


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
    public String viewHomePage(@RequestParam(required = false) Long categoryId, Model model) {
        List<Product> products;
        if (categoryId != null) {
            products = productService.getAllProducts()
                    .stream()
                    .filter(product -> product.getCategory() != null && product.getCategory().getId().equals(categoryId))
                    .collect(Collectors.toList());
        } else {
            products = productService.getAllProducts();
        }

        List<ProductDTO> productDTOs = products.stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("products", productDTOs);
        model.addAttribute("selectedCategoryId", categoryId);
        return "main";
    }



    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/addProduct")
    public String addProductPage(Model model) {
        model.addAttribute("product", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addProduct";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/saveProduct")
    public String saveProduct(@RequestParam("imageFile") MultipartFile imageFile, ProductDTO productDTO, @RequestParam("categoryId") Long categoryId) throws IOException {
        Category category = categoryService.findById(categoryId);

        if (!imageFile.isEmpty()) {
            String uploadDir = "uploads/";
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, imageFile.getBytes());

            productDTO.setImageUrl("/" + uploadDir + fileName);
        }

        Product product = ProductMapper.toEntity(productDTO, category);
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    @PostMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProductById(id);
        return "redirect:/main";
    }



}
