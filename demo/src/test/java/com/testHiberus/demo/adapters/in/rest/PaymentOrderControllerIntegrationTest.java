package com.testHiberus.demo.adapters.in.rest;

import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderRequest;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class PaymentOrderControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void shouldInitiateAndRetrievePaymentOrder() {
        // 1. Crear la solicitud
        PaymentOrderRequest request = new PaymentOrderRequest();
        request.setAmount(200.0);
        request.setCurrency("USD");
        request.setCreditorAccount("123456789");
        request.setDebtorAccount("987654321");

        PaymentOrderResponse response = webTestClient.post()
                .uri("/payment-initiation/payment-orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated() // Si aquí da 400, lee el punto 2 abajo
                .expectBody(PaymentOrderResponse.class)
                .returnResult()
                .getResponseBody();

        assertThat(response).isNotNull();
        String generatedId = response.getId();
        webTestClient.get()
                .uri("/payment-initiation/payment-orders/{id}", generatedId)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(generatedId);
    }
}