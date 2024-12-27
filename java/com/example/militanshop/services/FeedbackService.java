package com.example.militanshop.services;

import com.example.militanshop.models.DTO.FeedbackDTO;
import com.example.militanshop.models.Feedback;
import com.example.militanshop.models.Product;
import com.example.militanshop.repositories.FeedbackRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Transactional
    public void save(FeedbackDTO feedbackDTO, Product product) {

        Feedback feedback = new Feedback(feedbackDTO.getText(), feedbackDTO.getFilePath(), product);
        feedbackRepository.save(feedback);
    }

    public List<Feedback> getFeedbackByProduct(Product product) {
        return feedbackRepository.findByProduct(product);
    }
}
