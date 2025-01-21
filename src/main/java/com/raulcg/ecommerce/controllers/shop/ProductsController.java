package com.raulcg.ecommerce.controllers.shop;

import com.raulcg.ecommerce.enums.SortBy;
import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController("shopProductsController")
@RequestMapping("/api/v1/shop/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/get")
    public ResponseEntity<GenericApiResponse<List<Product>>> getAllProducts(
            @RequestParam(defaultValue = "") List<String> category,
            @RequestParam(defaultValue = "") List<String> brand,
            @RequestParam(defaultValue = "price-lowtohigh") SortBy sortBy
    ) {
        List<Product> products = productService.getFilteredProducts(category, brand, sortBy.getValue());
        return ResponseEntity.ok(new GenericApiResponse<>(true, products));
    }

    @RequestMapping("/get/{productId}")
    public ResponseEntity<GenericApiResponse<Product>> getProductDetails(@PathVariable UUID productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(new GenericApiResponse<>(true, product));
    }
}
