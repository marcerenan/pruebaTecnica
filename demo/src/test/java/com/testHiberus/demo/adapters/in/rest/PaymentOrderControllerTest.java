package com.testHiberus.demo.adapters.in.rest;

import com.testHiberus.demo.application.ports.in.PaymentOrderUseCase;
import com.testHiberus.demo.domain.model.PaymentOrder;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderRequest;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderResponse;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderStatusResponse;
import com.testHiberus.demo.infrastructure.rest.mapper.PaymentRestMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentOrderControllerTest {

    private WebTestClient webTestClient;

    @Mock
    private PaymentOrderUseCase useCase;

    @Mock
    private PaymentRestMapper mapper;

    @InjectMocks
    private PaymentOrderController controller;

    @BeforeEach
    void setUp() {
         webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void shouldReturn201WhenInitiatePaymentOrder() {
        // GIVEN
        PaymentOrderRequest requestDto = new PaymentOrderRequest();
        PaymentOrder domainModel = new PaymentOrder();
        PaymentOrder savedDomain = new PaymentOrder();
        PaymentOrderResponse responseDto = new PaymentOrderResponse();

        responseDto.setId("TX-123");
        responseDto.setStatus("PENDING");

        when(mapper.toDomain(any(PaymentOrderRequest.class))).thenReturn(domainModel);
        when(useCase.initiate(any(PaymentOrder.class))).thenReturn(Mono.just(savedDomain));
        when(mapper.toResponse(any(PaymentOrder.class))).thenReturn(responseDto);

        // WHEN & THEN
        webTestClient.post()
                .uri("/payment-initiation/payment-orders")
                .bodyValue(requestDto)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo("TX-123")
                .jsonPath("$.status").isEqualTo("PENDING");
    }

    @Test
    void shouldReturn200WhenRetrievePaymentOrder() {
        // GIVEN
        String id = "TX-123";
        PaymentOrder domainModel = new PaymentOrder();
        PaymentOrderResponse responseDto = new PaymentOrderResponse();
        responseDto.setId(id);

        when(useCase.getById(id)).thenReturn(Mono.just(domainModel));
        when(mapper.toResponse(domainModel)).thenReturn(responseDto);

        // WHEN & THEN
        webTestClient.get()
                .uri("/payment-initiation/payment-orders/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(id);
    }

    @Test
    void shouldReturn404WhenOrderDoesNotExist() {
        // GIVEN
        String id = "NOT-FOUND";
        when(useCase.getById(id)).thenReturn(Mono.empty());

        // WHEN & THEN
        webTestClient.get()
                .uri("/payment-initiation/payment-orders/{id}", id)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void shouldReturnStatusWhenRetrieveStatus() {
        // GIVEN
        String id = "TX-123";
        PaymentOrder domainModel = new PaymentOrder();
        PaymentOrderStatusResponse statusDto = new PaymentOrderStatusResponse();
        statusDto.setId(id);
        statusDto.setStatus("COMPLETED");

        when(useCase.getById(id)).thenReturn(Mono.just(domainModel));
        when(mapper.toStatusResponse(domainModel)).thenReturn(statusDto);

        // WHEN & THEN
        webTestClient.get()
                .uri("/payment-initiation/payment-orders/{id}/status", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.status").isEqualTo("COMPLETED");
    }
}