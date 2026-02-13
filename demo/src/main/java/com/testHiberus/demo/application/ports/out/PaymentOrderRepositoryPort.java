package com.testHiberus.demo.application.ports.out;

import com.testHiberus.demo.domain.model.PaymentOrder;
import reactor.core.publisher.Mono;

public interface PaymentOrderRepositoryPort {
    Mono<PaymentOrder> save(PaymentOrder order);
    Mono<PaymentOrder> findById(String id);
}
