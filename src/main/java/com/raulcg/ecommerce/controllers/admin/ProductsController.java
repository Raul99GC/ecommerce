package com.raulcg.ecommerce.controllers.admin;

import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.request.ProductRequest;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.responses.ImageUploadResponse;
import com.raulcg.ecommerce.services.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController("adminProductsController")
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
    public ResponseEntity<ImageUploadResponse> uploadProductImage(@RequestParam("my_file") MultipartFile my_file) throws Exception {
        String url = productService.handleImageUpload(my_file);
        ImageUploadResponse response = new ImageUploadResponse(true, url);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add")
    public ResponseEntity<GenericApiResponse<Product>> addProduct(@Validated @RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        return ResponseEntity.ok(new GenericApiResponse<>(true, product));
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
