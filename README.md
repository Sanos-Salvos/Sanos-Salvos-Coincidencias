# Sanos-Salvos-Coincidencias
Microservicio encargado de procesar coincidencias automáticas entre mascotas perdidas y encontradas utilizando Apache Kafka, JWT y Circuit Breaker con Resilience4j.

---

# Requisitos

- Java JDK 17
- Maven 3.8+
- Apache Kafka (puerto 9092)
- IntelliJ IDEA (opcional)
- Postman o Insomnia

---

# Instalación

## Clonar repositorio

```bash
git clone https://github.com/TU_USUARIO/Sanos-Salvos-Coincidencias.git
cd Sanos-Salvos-Coincidencias
```

## Instalar dependencias

### Linux / Mac

```bash
mvn clean install
```

### Windows

```powershell
.\mvnw clean install
```

---

# Configuración

Verificar:

```bash
src/main/resources/application.properties
```

Configuración principal:

```properties
server.port=8084
spring.application.name=microservicio-coincidencias

# Kafka
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=coincidencias-group

# JWT
jwt.secret=TuClaveJWT
jwt.expiration=3600000

# Circuit Breaker
resilience4j.circuitbreaker.instances.mascotasService.slidingWindowSize=10
resilience4j.circuitbreaker.instances.mascotasService.failureRateThreshold=50

```

---

# Ejecución
Antes de iniciar el microservicio, asegúrate de tener Apache Kafka ejecutándose.

## Desde IntelliJ

Ejecutar:

```bash
CoincidenciasApplication.java
```

## Desde consola

```bash
mvn spring-boot:run
```

El servicio quedará disponible en:

```bash
http://localhost:8084
```

---

# Pruebas API

## Endpoint Protegido con JWT

### GET

```http
GET http://localhost:8084/api/coincidencias/usuario/1
```

### Header requerido

```http
Authorization: Bearer TU_TOKEN_JWT
```

### Respuesta esperada

```json
[
  {
    "id": 101,
    "mascotaPerdidaId": 1,
    "mascotaEncontradaId": 4,
    "porcentajeCoincidencia": 95.5,
    "estado": "PENDIENTE_REVISION"
  }
]
```

---

# Prueba de Circuit Breaker

1. Apagar el microservicio `pet-service`.
2. Realizar nuevamente la petición de coincidencias.
3. El sistema ejecutará el método fallback evitando un error 500.

---

# Prueba Kafka

Enviar eventos al tópico:

```bash
mascotas-perdidas-topic
```

El listener consumirá automáticamente los eventos y generará coincidencias en segundo plano.

---

# Arquitectura

```text
Frontend
   ↓
BFF
   ↓
Coincidencias Service
   ↓
Kafka + Base de Datos
```

Tecnologías utilizadas:

- Spring Boot
- Apache Kafka
- Spring Security JWT
- Resilience4j Circuit Breaker
- H2 Database
- REST API