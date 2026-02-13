package com.testHiberus.demo.application.ports.in;

import com.testHiberus.demo.domain.model.ServiceStatus;

public interface GetServiceStatusUseCase {
    ServiceStatus execute();
}
