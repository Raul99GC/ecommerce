package com.raulcg.ecommerce.services.order;

import com.raulcg.ecommerce.enums.OrderStatus;
import com.raulcg.ecommerce.models.Order;
import com.raulcg.ecommerce.request.OrderRequest;

import java.util.List;
import java.util.UUID;

public interface IOrderService {

    Order createOrder(OrderRequest order);

    Order capturePayment(String token, UUID orderId, String payerId, UUID user);

    List<Order> getUserOrders(UUID userId);

    Order getOrderById(UUID orderId);

    List<Order> getOrders();

    Order updateStatusOrder(UUID orderId, OrderStatus status);
}
