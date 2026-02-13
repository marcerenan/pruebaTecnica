package com.testHiberus.demo.application.services;

import com.testHiberus.demo.application.ports.in.PaymentOrderUseCase;
import com.testHiberus.demo.application.ports.out.PaymentOrderRepositoryPort;
import com.testHiberus.demo.domain.model.PaymentOrder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class PaymentOrderService implements PaymentOrderUseCase {

    private final PaymentOrderRepositoryPort repositoryPort;

    public PaymentOrderService(PaymentOrderRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public Mono<PaymentOrder> initiate(PaymentOrder order) {
        order.setStatus("CREATED");
        if (order.getId() == null) {
            order.setId(java.util.UUID.randomUUID().toString());
        }
        // Llamamos al puerto para persistir
        return repositoryPort.save(order);

    }

    @Override
    public Mono<PaymentOrder> getById(String id) {
        return repositoryPort.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Payment order not found with id: " + id)));
    }

}