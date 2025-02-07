package com.raulcg.ecommerce.controllers.shop;

import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.services.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop/search")
public class SearchController {

    private final ProductService productService;

    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping("/{keywords}")
    public ResponseEntity<GenericApiResponse<List<Product>>> getSearchResults(@PathVariable String keywords) {
        List<Product> results = productService.searchProducts(keywords);
        return ResponseEntity.ok(new GenericApiResponse<>(true, results));
    }

}
