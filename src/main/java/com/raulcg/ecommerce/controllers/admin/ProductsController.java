package com.raulcg.ecommerce.controllers.admin;

import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.request.ProductRequest;
import com.raulcg.ecommerce.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/products")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadProductImage(@RequestBody MultipartFile my_file) throws Exception {
        productService.handleImageUpload(my_file);
        return ResponseEntity.ok("Image uploaded successfully");
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
        return ResponseEntity.ok("Product created successfully");
    }

    @PostMapping("/edit/{productId}")
    public ResponseEntity<?> editProduct(@RequestBody ProductRequest productRequest, @PathVariable UUID productId) {
        productService.editProductById(productRequest, productId);
        return ResponseEntity.ok("Product updated successfully");
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID productId) {
        productService.deleteProductById(productId);
        return ResponseEntity.ok("Product deleted successfully");
    }
}
