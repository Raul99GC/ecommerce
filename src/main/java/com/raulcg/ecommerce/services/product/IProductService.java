package com.raulcg.ecommerce.services.product;

import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.request.ProductRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface IProductService {

    String handleImageUpload(MultipartFile file) throws Exception;

    Product createProduct(ProductRequest product);

    List<Product> getAllProducts();

    void editProductById(ProductRequest product, UUID productId);

    void deleteProductById(UUID productId);

    List<Product> getFilteredProducts(List<String> categories, List<String> brands, String sortBy);

    Product getProductById(UUID productId);
}
