package com.raulcg.ecommerce.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {

    private int averageReview = 0;

    @NotBlank(message = "brand is required")
    private String brand;

    @NotBlank(message = "category is required")
    private String category;

    @NotBlank(message = "description is required")
    private String description;

    @NotBlank(message = "image is required")
    private String image;

    @Min(value = 1, message = "salePrice must be greater than 0")
    private int salePrice;

    @Min(value = 1, message = "price must be greater than 0")
    private double price;

    @NotBlank(message = "title is required")
    private String title;

    @Min(value = 1, message = "stock must be greater than 0")
    private int totalStock;
}