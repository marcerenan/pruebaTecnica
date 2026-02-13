package com.testHiberus.demo.infrastructure.rest.mapper;

import com.testHiberus.demo.domain.model.PaymentOrder;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderRequest;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderResponse;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderStatusResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentRestMapper {

    PaymentOrder toDomain(PaymentOrderRequest request);

    PaymentOrderResponse toResponse(PaymentOrder domain);

    PaymentOrderStatusResponse toStatusResponse(PaymentOrder domain);
}
