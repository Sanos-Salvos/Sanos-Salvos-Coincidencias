# Sanos-Salvos-Coincidencias

Motor de coincidencias entre mascotas perdidas y avistamientos

## Puerto

8084

## Base de datos

coincidencias_db

## Endpoints disponibles

GET /api/coincidencias
POST /api/coincidencias
GET /api/coincidencias/{id}
PUT /api/coincidencias/{id}/estado
DELETE /api/coincidencias/{id}

## Ejecucion con Docker

docker-compose up --build

## Ejecucion manual

mvn spring-boot:run

## Tecnologias

- Java 21
- Spring Boot 3.2
- Spring Security + JWT
- PostgreSQL
- Docker
