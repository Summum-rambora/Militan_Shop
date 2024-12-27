package com.example.militanshop.controllers;

import com.example.militanshop.models.DTO.FeedbackDTO;
import com.example.militanshop.models.Feedback;
import com.example.militanshop.models.Product;
import com.example.militanshop.services.FileService;
import com.example.militanshop.services.FeedbackService;
import com.example.militanshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FeedbackController {

    @Autowired
    private FileService fileService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private ProductService productService;

    @PostMapping("/uploadFeedback")
    public String uploadFeedback(@RequestParam("file") MultipartFile file,
                                 @RequestParam("feedback") String feedbackText,
                                 @RequestParam("productId") Long productId) {
        try {

            String filePath = fileService.saveFile(file);


            Product product = productService.findProductById(productId);


            FeedbackDTO feedbackDTO = new FeedbackDTO(feedbackText, filePath, product);

            feedbackService.save(feedbackDTO, product);

        } catch (IOException e) {
            e.printStackTrace();

        }

        return "redirect:/product/" + productId;
    }
}
