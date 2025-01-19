package com.raulcg.ecommerce.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductRequest {
    private String title;
    private String description;
    private double price;
    private String image;
    private String category;
    private String brand;
    private int stock;
    private int averageReview;
}