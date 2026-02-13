package com.testHiberus.demo.infrastructure.persistence.adapter;

import com.testHiberus.demo.domain.model.PaymentOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentPersistenceAdapterIT {

    @Autowired
    private PaymentPersistenceAdapter adapter;

    @Test
    void shouldSaveAndFindPaymentOrder() {
        // GIVEN
        PaymentOrder order = new PaymentOrder();
        order.setId("PAY-H2-001");
        order.setAmount(50.0);
        order.setCurrency("EUR");
        order.setStatus("CREATED");

        // WHEN
        var result = adapter.save(order)
                .then(adapter.findById("PAY-H2-001"));

        // THEN
        StepVerifier.create(result)
                .assertNext(foundOrder -> {
                    assertThat(foundOrder.getId()).isEqualTo("PAY-H2-001");
                    assertThat(foundOrder.getAmount()).isEqualTo(50.0);
                })
                .verifyComplete();
    }
}