package com.raulcg.ecommerce.utils;

import com.paypal.sdk.Environment;
import com.paypal.sdk.PaypalServerSdkClient;
import com.paypal.sdk.authentication.ClientCredentialsAuthModel;
import com.paypal.sdk.controllers.OrdersController;
import com.paypal.sdk.exceptions.ApiException;
import com.paypal.sdk.models.*;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

@Service
public class PaypalService {

    @Value("${paypal.api.clientId}")
    private String paypalClientId;

    @Value("${paypal.api.secret}")
    private String paypalSecret;

    @Value("${frontend.url}")
    private String frontendUrl;

    public String createPaypalPaymentLink(Long amount, UUID orderId) throws IOException, ApiException {
        // Configuración del cliente de PayPal
        PaypalServerSdkClient client = new PaypalServerSdkClient.Builder()
                .loggingConfig(builder -> builder
                        .level(Level.DEBUG)
                        .requestConfig(logConfigBuilder -> logConfigBuilder.body(true))
                        .responseConfig(logConfigBuilder -> logConfigBuilder.headers(true)))
                .httpClientConfig(configBuilder -> configBuilder
                        .timeout(0))
                .clientCredentialsAuth(new ClientCredentialsAuthModel.Builder(
                        paypalClientId,
                        paypalSecret
                ).build())
                .environment(Environment.SANDBOX)
                .build();

        // Configurar el controlador y la solicitud de orden
        OrdersController ordersController = client.getOrdersController();

        BlikExperienceContext blikExperienceContext = new BlikExperienceContext.Builder()
                .returnUrl(frontendUrl + "/shop/paypal-return?orderId=" + orderId)
                .cancelUrl(frontendUrl + "/shop/paypal-cancel?orderId=" + orderId)
                .build();

        BlikPaymentRequest blikPaymentRequest = new BlikPaymentRequest.Builder()
                .experienceContext(blikExperienceContext)
                .build();

        OrdersCreateInput ordersCreateInput = new OrdersCreateInput.Builder(
                null,
                new OrderRequest(
                        CheckoutPaymentIntent.CAPTURE,
                        Arrays.asList(
                                new PurchaseUnitRequest.Builder(
                                        new AmountWithBreakdown.Builder(
                                                "USD",
                                                amount.toString()
                                        ).build()
                                ).build()
                        ),
                        null,
                        new PaymentSource.Builder()
                                .blik(blikPaymentRequest)
                                .build(),
                        null
                )
        ).prefer("return=minimal").build();

        // Generar y retornar la URL del pago
        return ordersController.ordersCreate(ordersCreateInput)
                .getResult().getLinks().get(1).getHref();
    }

}
