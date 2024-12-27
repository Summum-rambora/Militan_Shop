package com.example.militanshop.models.DTO.Mappers;

import com.example.militanshop.models.Category;
import com.example.militanshop.models.DTO.ProductDTO;
import com.example.militanshop.models.Product;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImageUrl(),
                product.getCategory() != null ? product.getCategory().getId() : null,
                product.getCategory() != null ? product.getCategory().getName() : null
        );
    }


    public static Product toEntity(ProductDTO productDTO, Category category) {
        if (productDTO == null) {
            return null;
        }
        return new Product(
                productDTO.getId(),
                productDTO.getName(),
                productDTO.getDescription(),
                productDTO.getPrice(),
                productDTO.getImageUrl(),
                category
        );
    }
}
