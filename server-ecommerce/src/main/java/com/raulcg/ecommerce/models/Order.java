package com.raulcg.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.raulcg.ecommerce.enums.OrderStatus;
import com.raulcg.ecommerce.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private List<OrderItem> orderItems = new ArrayList<>();

    private OrderStatus orderStatus;
    private String paymentMethod;
    private PaymentStatus paymentStatus;
    private Long totalAmount;

    private String token;
    private String payerId;

    @Embedded
    private AddressInfo addressInfo;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime orderDate;
    @UpdateTimestamp
    private LocalDateTime orderUpdateDate;
}