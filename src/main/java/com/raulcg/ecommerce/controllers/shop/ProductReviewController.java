package com.raulcg.ecommerce.controllers.shop;

import com.raulcg.ecommerce.models.ProductReview;
import com.raulcg.ecommerce.request.ProductReviewDTO;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.services.productReview.ProductReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shop/review")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @PostMapping("/add")
    public ResponseEntity<GenericApiResponse<ProductReview>> addReview(@RequestBody ProductReviewDTO reviewDTO) {
        ProductReview savedReview = productReviewService.addReview(reviewDTO);
        GenericApiResponse<ProductReview> response = new GenericApiResponse<>(true, savedReview);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<GenericApiResponse<List<ProductReview>>> getReviewsByProduct(@PathVariable UUID productId) {
        List<ProductReview> reviews = productReviewService.getReviewsByProduct(productId);
        GenericApiResponse<List<ProductReview>> response = new GenericApiResponse<>(true, reviews);
        return ResponseEntity.ok(response);
    }
}