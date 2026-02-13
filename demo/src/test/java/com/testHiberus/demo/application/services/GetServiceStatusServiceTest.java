package com.testHiberus.demo.application.services;

import com.testHiberus.demo.domain.model.ServiceStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GetServiceStatusServiceTest {

    private final GetServiceStatusService service = new GetServiceStatusService();

    @Test
    void shouldReturnRunningStatus() {

        ServiceStatus result = service.execute();

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("Service is running");
    }
}
