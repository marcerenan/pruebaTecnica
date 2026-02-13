package com.testHiberus.demo.adapters.in.rest.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;



class GlobalExceptionHandlerTest {

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {

        this.webTestClient = WebTestClient.bindToController(new TestController())
                .controllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void whenExceptionIsThrown_thenHandleAllExceptionsShouldReturnBadRequest() {
        String errorMessage = "Simulated error";

        webTestClient.get()
                .uri("/test-error")
                .exchange()
                .expectStatus().isBadRequest() // Verifica que sea 400
                .expectBody()
                .jsonPath("$.status").isEqualTo(HttpStatus.BAD_REQUEST.value())
                .jsonPath("$.message").isEqualTo("Error procesando la orden: " + errorMessage)
                .jsonPath("$.timestamp").exists();
    }

    @RestController
    private static class TestController {
        @GetMapping("/test-error")
        public Mono<String> throwError() {
            return Mono.error(new RuntimeException("Simulated error"));
        }
    }
}