package com.testHiberus.demo.adapters.in.rest;

import com.testHiberus.demo.application.ports.in.GetServiceStatusUseCase;
import com.testHiberus.demo.domain.model.ServiceStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class StatusControllerTest {

    private GetServiceStatusUseCase useCase;
    private StatusController controller;

    @BeforeEach
    void setup() {
        useCase = mock(GetServiceStatusUseCase.class);
        controller = new StatusController(useCase);
    }

    @Test
    void shouldReturnServiceStatus() {

        ServiceStatus status = new ServiceStatus("1001");
        when(useCase.execute()).thenReturn(status);

        ServiceStatus result = controller.getStatus();

        assertThat(result).isEqualTo(status);
        assertThat( result.getStatus() ).isEqualTo( status.getStatus() );
        verify(useCase).execute();
    }
}
