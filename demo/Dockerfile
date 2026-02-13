# --- Etapa 1: Build ---
FROM maven:3.8.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el POM y descargar dependencias (esto optimiza la cache de Docker)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# --- Etapa 2: Runtime ---
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copiar solo el artefacto generado desde la etapa de build
COPY --from=build /app/target/*.jar app.jar

# Configuración de variables de entorno por defecto
ENV JAVA_OPTS=""

# Exponer el puerto de Spring Boot
EXPOSE 8080

# Ejecutar la aplicación
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]