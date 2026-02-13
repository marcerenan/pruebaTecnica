package com.testHiberus.demo.infrastructure.rest.mapper;

import com.testHiberus.demo.domain.model.PaymentOrder;
import com.testHiberus.demo.infrastructure.rest.adapter.dto.PaymentOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentRestMapperTest {

    private PaymentRestMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new PaymentRestMapperImpl();
    }

    @Test
    void shouldMapRequestToDomain() {
        // GIVEN
        PaymentOrderRequest request = new PaymentOrderRequest();
        request.setAmount(100.0);
        request.setCurrency("EUR");

        // WHEN
        PaymentOrder result = mapper.toDomain(request);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getAmount()).isEqualTo(100.0);
        assertThat(result.getCurrency()).isEqualTo("EUR");
    }
}