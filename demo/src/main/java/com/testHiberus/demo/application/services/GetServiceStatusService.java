package com.testHiberus.demo.application.services;

import com.testHiberus.demo.application.ports.in.GetServiceStatusUseCase;
import com.testHiberus.demo.domain.model.ServiceStatus;
import org.springframework.stereotype.Service;

@Service
public class GetServiceStatusService implements GetServiceStatusUseCase {

    @Override
    public ServiceStatus execute() {
        return new ServiceStatus("Service is running");
    }
}
