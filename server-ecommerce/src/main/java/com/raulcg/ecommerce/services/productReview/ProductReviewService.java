package com.raulcg.ecommerce.services.productReview;

import com.raulcg.ecommerce.exceptions.ProductNotFoundException;
import com.raulcg.ecommerce.exceptions.UserNotFoundException;
import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.models.ProductReview;
import com.raulcg.ecommerce.models.User;
import com.raulcg.ecommerce.repositories.ProductRepository;
import com.raulcg.ecommerce.repositories.ProductReviewRepository;
import com.raulcg.ecommerce.repositories.UserRepository;
import com.raulcg.ecommerce.request.ProductReviewDTO;
import com.raulcg.ecommerce.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductReviewService implements IProductReviewService {

    private final ProductReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    private ProductService productService;

    public ProductReviewService(ProductReviewRepository reviewRepository,
                                ProductRepository productRepository,
                                UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Autowired(required = false)
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ProductReview addReview(ProductReviewDTO reviewDTO) {

        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Producto no encontrado"));

        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));

        ProductReview review = new ProductReview();
        review.setReviewMessage(reviewDTO.getReviewMessage());

        review.setUserName(reviewDTO.getUserName());
        review.setReviewValue(reviewDTO.getReviewValue());
        review.setProduct(product);
        review.setUser(user);

        ProductReview savedReview = reviewRepository.save(review);
        if (productService != null) {
            productService.updateProductAverage(product);
        }
        return savedReview;
    }

    @Override
    public List<ProductReview> getReviewsByProduct(UUID productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException("Producto no encontrado");
        }
        return reviewRepository.findByProductId(productId);
    }
}