package com.raulcg.ecommerce.services.order;

import com.raulcg.ecommerce.enums.OrderStatus;
import com.raulcg.ecommerce.enums.PaymentStatus;
import com.raulcg.ecommerce.exceptions.EnoughProductStockException;
import com.raulcg.ecommerce.exceptions.OrderNotFoundException;
import com.raulcg.ecommerce.exceptions.ProductNotFoundException;
import com.raulcg.ecommerce.exceptions.UserNotFoundException;
import com.raulcg.ecommerce.models.*;
import com.raulcg.ecommerce.repositories.*;
import com.raulcg.ecommerce.request.OrderRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public OrderService(UserRepository userRepository, ModelMapper modelMapper, CartRepository cartRepository, OrderRepository orderRepository, CartItemRepository orderItemRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.cartItemRepository = orderItemRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    @Override
    public Order createOrder(OrderRequest order) {
        Order newOrder = new Order();
        User user = userRepository.findById(order.getUserId()).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        newOrder.setUser(user);

        List<CartItem> userCartItems = cartRepository.findByUserId(user.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"))
                .getItems();


        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem cartItem : userCartItems) {
            Product product = cartItem.getProduct();

            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setTitle(product.getTitle());
            orderItem.setImage(product.getImage());
            orderItem.setPrice(product.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItems.add(orderItem);
            orderItem.setOrder(newOrder);

            orderItems.add(orderItem);
        }
        newOrder.setOrderItems(orderItems);

        newOrder.setOrderStatus(order.getOrderStatus());
        newOrder.setPaymentMethod(order.getPaymentMethod());
        newOrder.setPaymentStatus(order.getPaymentStatus());
        newOrder.setTotalAmount(order.getTotalAmount());

        AddressInfo addressInfo = modelMapper.map(order.getAddressInfo(), AddressInfo.class);
        newOrder.setAddressInfo(addressInfo);
        orderRepository.save(newOrder);

        return newOrder;
    }

    @Transactional
    @Override
    public Order capturePayment(String token, UUID orderId, String payerId, UUID user) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        // Agrupar los items del pedido por productId y sumar la cantidad de cada uno.
        Map<UUID, Integer> productQuantities = order.getOrderItems().stream()
                .collect(Collectors.groupingBy(
                        OrderItem::getProductId,
                        Collectors.summingInt(OrderItem::getQuantity)
                ));

        // Para cada producto, validar stock y actualizar.
        productQuantities.forEach((productId, totalQuantity) -> {
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            if (product.getTotalStock() < totalQuantity) {
                throw new EnoughProductStockException("Not enough stock for this product " + product.getTitle());
            }
            // Resta el stock necesario
            product.setTotalStock(product.getTotalStock() - totalQuantity);
            productRepository.save(product);
        });

        order.setToken(token);
        order.setPayerId(payerId);
        order.setPaymentStatus(PaymentStatus.PAID);

        // Quitar los items del carrito del usuario.
        cartItemRepository.deleteByCart_userId(user);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getUserOrders(UUID userId) {
        return orderRepository.findAllByUserId(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order not found"));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Transactional
    @Override
    public Order updateStatusOrder(UUID orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new OrderNotFoundException("Order not found"));
        order.setOrderStatus(status);
        orderRepository.save(order);
        return order;
    }


}
