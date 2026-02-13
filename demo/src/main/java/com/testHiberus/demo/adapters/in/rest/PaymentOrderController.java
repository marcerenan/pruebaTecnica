package com.testHiberus.demo.adapters.in.rest;

import com.testHiberus.demo.application.ports.in.PaymentOrderUseCase;
import com.testHiberus.demo.infrastructure.rest.adapter.api.PaymentOrderApi;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderRequest;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderResponse;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderStatusResponse;
import com.testHiberus.demo.infrastructure.rest.mapper.PaymentRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
public class PaymentOrderController implements PaymentOrderApi {

    private final PaymentOrderUseCase useCase;
    private final PaymentRestMapper mapper;

    public PaymentOrderController(PaymentOrderUseCase useCase, PaymentRestMapper mapper) {
        this.useCase = useCase;
        this.mapper = mapper;
    }



    @Override
    public Mono<ResponseEntity<PaymentOrderResponse>> initiatePaymentOrder(Mono<PaymentOrderRequest> paymentOrderRequest, ServerWebExchange exchange) {
        return paymentOrderRequest
                .map(mapper::toDomain)
                .flatMap(useCase::initiate)
                .map(mapper::toResponse)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));

    }

    @Override
    public Mono<ResponseEntity<PaymentOrderResponse>> retrievePaymentOrder(String id, ServerWebExchange exchange) {
        return useCase.getById(id)
                .map(mapper::toResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @Override
    public Mono<ResponseEntity<PaymentOrderStatusResponse>> retrievePaymentOrderStatus(String id, ServerWebExchange exchange)  {
        return useCase.getById(id)
                .map(mapper::toStatusResponse)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
