package com.testHiberus.demo.infrastructure.persistence.repository;

import com.testHiberus.demo.infrastructure.persistence.entity.PaymentOrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Repositorio reactivo utilizando R2DBC.
 * No requiere implementación manual; Spring Data genera el código en tiempo de ejecución.
 */
@Repository
public interface SpringDataPaymentRepository extends ReactiveCrudRepository<PaymentOrderEntity, Long> {

    /**
     * Busca una orden por su ID de negocio (externalId).
     * @param externalId El ID que viene del dominio.
     * @return Un Mono con la entidad encontrada o vacío.
     */
    Mono<PaymentOrderEntity> findByExternalId(String externalId);
}