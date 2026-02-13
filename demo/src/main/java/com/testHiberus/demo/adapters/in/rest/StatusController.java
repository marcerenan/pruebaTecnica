package com.testHiberus.demo.adapters.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.testHiberus.demo.application.ports.in.GetServiceStatusUseCase;
import com.testHiberus.demo.domain.model.ServiceStatus;

@RestController
public class StatusController {

    private final GetServiceStatusUseCase useCase;

    public StatusController(GetServiceStatusUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping("/status")
    public ServiceStatus getStatus() {
        return useCase.execute();
    }
}