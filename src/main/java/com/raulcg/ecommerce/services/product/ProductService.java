package com.raulcg.ecommerce.services.product;

import com.raulcg.ecommerce.exceptions.ProductNotFoundException;
import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.repositories.ProductRepository;
import com.raulcg.ecommerce.request.ProductRequest;
import com.raulcg.ecommerce.utils.CloudinaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CloudinaryUtils cloudinaryUtils;

    @Override
    public String handleImageUpload(MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            String base64 = Base64.getEncoder().encodeToString(bytes);
            String base64Url = "data:" + file.getContentType() + ";base64," + base64;

            return cloudinaryUtils.uploadImage("image.jpg", file);

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void createProduct(ProductRequest product) {
        Product newProduct = new Product();
        newProduct.setTitle(product.getTitle());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setImage(product.getImage());
        newProduct.setCategory(product.getCategory());
        newProduct.setBrand(product.getBrand());
        newProduct.setStock(product.getStock());
        newProduct.setAverageReview(product.getAverageReview());
        productRepository.save(newProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void editProductById(ProductRequest product, UUID productId) {
        Product productSaved = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productSaved.setTitle(product.getTitle());
        productSaved.setDescription(product.getDescription());
        productSaved.setPrice(product.getPrice());
        productSaved.setImage(product.getImage());
        productSaved.setCategory(product.getCategory());
        productSaved.setBrand(product.getBrand());
        productSaved.setStock(product.getStock());
        productSaved.setAverageReview(product.getAverageReview());

        productRepository.save(productSaved);
    }

    @Override
    public void deleteProductById(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.deleteById(product.getId());
    }
}
