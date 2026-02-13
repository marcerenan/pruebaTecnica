package com.testHiberus.demo.infrastructure.persistence.adapter;

import com.testHiberus.demo.application.ports.out.PaymentOrderRepositoryPort;
import com.testHiberus.demo.domain.model.PaymentOrder;
import com.testHiberus.demo.infrastructure.persistence.mapper.PaymentPersistenceMapper;
import com.testHiberus.demo.infrastructure.persistence.repository.SpringDataPaymentRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PaymentPersistenceAdapter implements PaymentOrderRepositoryPort {

    private final SpringDataPaymentRepository repository;
    private final PaymentPersistenceMapper mapper;


    public PaymentPersistenceAdapter(SpringDataPaymentRepository repository, PaymentPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Mono<PaymentOrder> save(PaymentOrder order) {
        return Mono.just(order)
                .map(mapper::toEntity)
                .flatMap(repository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<PaymentOrder> findById(String id) {
        return repository.findByExternalId(id)
                .map(mapper::toDomain);
    }
}