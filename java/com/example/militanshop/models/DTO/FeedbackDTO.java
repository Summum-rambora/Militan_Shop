package com.example.militanshop.models.DTO;

import com.example.militanshop.models.Product;

public class FeedbackDTO {
    private String text;
    private String filePath;
    private Product product;

    public FeedbackDTO() {}

    public FeedbackDTO(String text, String filePath, Product product) {
        this.text = text;
        this.filePath = filePath;
        this.product = product;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
