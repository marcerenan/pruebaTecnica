# Mi Proyecto de prueba
Test de prueba. Proyecto de microservicios de creación
y consulta de Ordenes de pago.

## Requerimientos
* **Java 17**
* **Spring Boot 3.x** (WebFlux)

## Instalación y Ejecución
# Compilación local:
.\mvnw clean install
# Test
.\mvnw test
* para ver el reporte: /target/site/jacoco/index.html
# Ejecución local
.\mvnw spring-boot:run

## Instalación en Docker
docker-compose up
* puerto por default 8080

## Servicios
* POST /payment-initiation/payment-orders
Request:
  {
  "amount": 150,
  "currency": "EUR",
  "debtorAccount": "ES12345678901234567890",
  "creditorAccount": "ES09876543210987654321"
  }
Response:
  {
  "id": "8577704d-ab85-4fc1-afd0-db97afdfaa61",
  "status": "CREATED"
  }
* GET /payment-orders/{id}/status
  Response:
  {
  "id":"id",
  "status":"CREATED"
  }

* GET /payment-orders/{id}
  Response:
  {
  "id":"id",
  "status":"CREATED"
  }