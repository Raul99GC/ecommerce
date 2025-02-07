package com.raulcg.ecommerce.controllers.admin;

import com.raulcg.ecommerce.enums.OrderStatus;
import com.raulcg.ecommerce.models.Order;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.services.order.OrderService;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/orders")
public class OrderAdminController {

    private final OrderService orderService;

    public OrderAdminController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get")
    public ResponseEntity<GenericApiResponse<List<Order>>> getOrders() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(new GenericApiResponse<>(true, orders));
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<GenericApiResponse<Order>> getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(new GenericApiResponse<>(true, order));
    }

    @PutMapping("/update-status/{orderId}")
    public ResponseEntity<GenericApiResponse<Order>> updateOrder(@PathVariable UUID orderId, @NotNull @RequestParam OrderStatus orderStatus) {

        Order order = orderService.updateStatusOrder(orderId, orderStatus);
        return ResponseEntity.ok(new GenericApiResponse<>(true, order));
    }
}
