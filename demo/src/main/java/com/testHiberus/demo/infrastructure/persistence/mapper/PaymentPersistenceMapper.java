package com.testHiberus.demo.infrastructure.persistence.mapper;

import com.testHiberus.demo.domain.model.PaymentOrder;
import com.testHiberus.demo.infrastructure.persistence.entity.PaymentOrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentPersistenceMapper {

    /**
     * Convierte el modelo de Dominio a la Entidad de Base de Datos.
     * * - El 'id' de la entidad (Long) se ignora porque es autoincremental en Postgres.
     * - El 'id' del dominio (String) se mapea al 'externalId' de la tabla.
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "externalId", source = "id")
    PaymentOrderEntity toEntity(PaymentOrder domain);

    /**
     * Convierte la Entidad de Base de Datos al modelo de Dominio.
     * * - El 'externalId' de la base de datos vuelve a ser el 'id' del dominio.
     */
    @Mapping(target = "id", source = "externalId")
    PaymentOrder toDomain(PaymentOrderEntity entity);
}