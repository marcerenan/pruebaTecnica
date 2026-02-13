package com.testHiberus.demo.application.services;

import com.testHiberus.demo.application.ports.out.PaymentOrderRepositoryPort;
import com.testHiberus.demo.domain.model.PaymentOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentOrderServiceTest {

    @Mock
    private PaymentOrderRepositoryPort repositoryPort;

    private PaymentOrderService paymentOrderService;

    @BeforeEach
    void setUp() {
        // Inyección manual por constructor (ya que no usamos @InjectMocks ni Lombok)
        paymentOrderService = new PaymentOrderService(repositoryPort);
    }

    @Test
    @DisplayName("Debería asignar estado CREATED y guardar la orden correctamente")
    void shouldAssignCreatedStatusAndSaveOrder() {
        // GIVEN: Una orden de entrada sin estado
        PaymentOrder inputOrder = new PaymentOrder();
        inputOrder.setId("ORD-123");
        inputOrder.setAmount(100.0);
        inputOrder.setCurrency("EUR");

        // Configuramos el mock para que cuando se guarde, devuelva el mismo objeto
        // (Simulando el comportamiento del adaptador de persistencia)
        when(repositoryPort.save(any(PaymentOrder.class)))
                .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

        // WHEN: Ejecutamos el método del servicio
        Mono<PaymentOrder> result = paymentOrderService.initiate(inputOrder);

        // THEN: Verificamos con StepVerifier (imprescindible para WebFlux)
        StepVerifier.create(result)
                .assertNext(savedOrder -> {
                    assertThat(savedOrder).isNotNull();
                    assertThat(savedOrder.getId()).isEqualTo("ORD-123");
                    // Validamos la lógica de negocio: el servicio debe poner el estado
                    assertThat(savedOrder.getStatus()).isEqualTo("CREATED");
                    assertThat(savedOrder.getAmount()).isEqualTo(100.0);
                })
                .verifyComplete();

        // Verificamos que el puerto fue llamado exactamente una vez
        verify(repositoryPort, times(1)).save(any(PaymentOrder.class));
    }

    @Test
    @DisplayName("Debería recuperar una orden por ID a través del puerto")
    void shouldFindOrderById() {
        // GIVEN
        PaymentOrder mockOrder = new PaymentOrder();
        mockOrder.setId("ORD-999");
        mockOrder.setStatus("COMPLETED");

        when(repositoryPort.findById("ORD-999")).thenReturn(Mono.just(mockOrder));

        // WHEN
        Mono<PaymentOrder> result = paymentOrderService.getById("ORD-999");

        // THEN
        StepVerifier.create(result)
                .expectNextMatches(order -> order.getId().equals("ORD-999")
                        && order.getStatus().equals("COMPLETED"))
                .verifyComplete();

        verify(repositoryPort, times(1)).findById("ORD-999");
    }
}