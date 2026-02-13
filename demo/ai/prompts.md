# Registro de Prompts IA

## Prompt 01 – Resumen WSDL y mapping BIAN
Actúa como arquitecto de software especializado en integración bancaria y estándar BIAN.
Te proporcionaré el contenido de un WSDL correspondiente a un servicio legado SOAP. 
Necesito que:
1. Analices el WSDL e identifiques:
2. Generes un resumen estructurado del servicio que incluya:
3. Propongas un mapping preliminar hacia el estándar BIAN:
4. Señala posibles problemas de diseño del servicio legado (si los hay) que deban corregirse en la migración a arquitectura REST contract-first.
El resultado debe ser estructurado, técnico y orientado a documentación arquitectónica.

## Prompt 02 – Borrador OpenAPI
Actúa como arquitecto de software especializado en modernización de servicios bancarios y diseño contract-first con OpenAPI 3.0.
Contexto:
Se ha analizado un servicio legado SOAP definido en un WSDL correspondiente a PaymentOrderService.
Se identificaron las siguientes operaciones:
- SubmitPaymentOrder
- GetPaymentOrderStatus
Se propuso el siguiente mapping preliminar a BIAN:
Service Domain: Payments Initiation
Operaciones REST esperadas:
- POST /payment-initiation/payment-orders
- GET /payment-initiation/payment-orders/{id}
- GET /payment-initiation/payment-orders/{id}/status
Necesito que:
1. Generes un archivo openapi.yaml compatible con OpenAPI 3.0.3.
2. No generes código Java, solo el contrato YAML.
El resultado debe ser estructurado, técnicamente consistente y listo para ser utilizado por openapi-generator.

## Prompt 03 – Arquitectura Hexagonal
Actúa como arquitecto de software experto en Spring Boot 3, arquitectura hexagonal (Ports & Adapters) y diseño de microservicios bancarios.
Contexto:
Se dispone de un contrato OpenAPI 3.0 previamente definido para el dominio Payments Initiation, que expone operaciones REST relacionadas con la gestión de órdenes de pago.
Necesito que generes los pasos a seguir para generar la estructura para una arquitectura hexagonal para un microservicio basado en:
- Java 17+
- Spring Boot 3+
- Contract-first con OpenAPI
- Arquitectura Ports & Adapters

## Prompt 04 – Generación de tests
Para la siguiente clase, actuando como desarrollador de software especializado en spring webflux, generar el código de una clase de test unitario.
Utiliza JUnit 5 y Mockito
Utiliza StepVerifier de Project Reactor para validar los flujos.
