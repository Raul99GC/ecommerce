package com.raulcg.ecommerce.services.productReview;

import com.raulcg.ecommerce.models.ProductReview;
import com.raulcg.ecommerce.request.ProductReviewDTO;

import java.util.List;
import java.util.UUID;

public interface IProductReviewService {

    ProductReview addReview(ProductReviewDTO reviewDTO);

    List<ProductReview> getReviewsByProduct(UUID productId);
}
