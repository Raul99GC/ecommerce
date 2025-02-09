package com.raulcg.ecommerce.controllers.shop;

import com.paypal.sdk.exceptions.ApiException;
import com.raulcg.ecommerce.models.Order;
import com.raulcg.ecommerce.request.CaptureOrderRequest;
import com.raulcg.ecommerce.request.OrderRequest;
import com.raulcg.ecommerce.responses.GenericApiResponse;
import com.raulcg.ecommerce.responses.NewOrderResponse;
import com.raulcg.ecommerce.services.order.OrderService;
import com.raulcg.ecommerce.utils.AuthUtils;
import com.raulcg.ecommerce.utils.PaypalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shop/order")
public class OrderShopController {

    private final AuthUtils authUtils;

    private final OrderService orderService;
    private final PaypalService paypalService;

    public OrderShopController(AuthUtils authUtils, OrderService orderService, PaypalService paypalService) {
        this.authUtils = authUtils;
        this.orderService = orderService;
        this.paypalService = paypalService;
    }

    @PostMapping("/create")
    public ResponseEntity<NewOrderResponse> createOrder(@Valid @RequestBody OrderRequest req) throws IOException, ApiException {
        Order order = orderService.createOrder(req);

        String approvalURL = paypalService.createPaypalPaymentLink(order.getTotalAmount(), order.getId());

        NewOrderResponse newOrderResponse = new NewOrderResponse(true, approvalURL, order.getId());

        return new ResponseEntity<>(newOrderResponse, HttpStatus.CREATED);
    }

    @PostMapping("/capture")
    public ResponseEntity<GenericApiResponse<Order>> captureOrder(@Valid @RequestBody CaptureOrderRequest request) {

        UUID userId = authUtils.loggedInUser().get().getId();

        Order order = orderService.capturePayment(request.getToken(), request.getOrderId(), request.getPayerId(), userId);
        GenericApiResponse<Order> response = new GenericApiResponse<>(true, order, "Order confirmed");
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<GenericApiResponse<List<Order>>> getUserOrders(@PathVariable UUID userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        GenericApiResponse<List<Order>> response = new GenericApiResponse<>(true, orders, "Orders retrieved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/details/{orderId}")
    public ResponseEntity<GenericApiResponse<Order>> getOrderById(@PathVariable UUID orderId) {
        Order order = orderService.getOrderById(orderId);
        GenericApiResponse<Order> response = new GenericApiResponse<>(true, order, "Order retrieved");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
