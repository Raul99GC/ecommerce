package com.raulcg.ecommerce.services.product;

import com.raulcg.ecommerce.exceptions.ProductNotFoundException;
import com.raulcg.ecommerce.models.Product;
import com.raulcg.ecommerce.repositories.ProductRepository;
import com.raulcg.ecommerce.request.ProductRequest;
import com.raulcg.ecommerce.utils.CloudinaryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Product createProduct(ProductRequest product) {
        Product newProduct = new Product();
        newProduct.setTitle(product.getTitle());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setImage(product.getImage());
        newProduct.setCategory(product.getCategory());
        newProduct.setBrand(product.getBrand());
        newProduct.setTotalStock(product.getTotalStock());
        newProduct.setAverageReview(product.getAverageReview());
        return productRepository.save(newProduct);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void editProductById(ProductRequest product, UUID productId) {
        Product productSaved = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));

        productSaved.setTitle(product.getTitle());
        productSaved.setDescription(product.getDescription());
        productSaved.setPrice(product.getPrice());
        productSaved.setImage(product.getImage());
        productSaved.setCategory(product.getCategory());
        productSaved.setBrand(product.getBrand());
        productSaved.setTotalStock(product.getTotalStock());
        productSaved.setAverageReview(product.getAverageReview());

        productRepository.save(productSaved);
    }

    @Override
    @Transactional
    public void deleteProductById(UUID productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        productRepository.deleteById(product.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getFilteredProducts(List<String> categories, List<String> brands, String sortBy) {

        if (categories == null || categories.isEmpty()) {
            categories = null;
        }
        if (brands == null || brands.isEmpty()) {
            brands = null;
        }
        return productRepository.findFilteredProducts(categories, brands, sortBy);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Product> searchProducts(String keywords) {
        return productRepository.searchProducts(keywords);
    }

    public void updateProductAverage(Product product) {
        Integer avg = productRepository.findAverageRatingByProductId(product.getId());
        product.setAverageReview(avg != null ? avg : 0);
        productRepository.save(product);
    }
}
