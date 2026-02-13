package com.testHiberus.demo.application.ports.in;

import com.testHiberus.demo.domain.model.PaymentOrder;
import reactor.core.publisher.Mono;

public interface PaymentOrderUseCase {
    Mono<PaymentOrder> initiate(PaymentOrder order);
    Mono<PaymentOrder> getById(String id);
}
