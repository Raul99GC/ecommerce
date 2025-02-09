package com.raulcg.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String image;

    private String title;

    private String description;

    private String category;

    private String brand;

    private double price;

    private int totalStock;

    private int averageReview;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    private Set<CartItem> cartItems;

    @OneToMany(mappedBy = "product")
    @JsonManagedReference
    List<ProductReview> reviews;

    @JsonIgnore
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
