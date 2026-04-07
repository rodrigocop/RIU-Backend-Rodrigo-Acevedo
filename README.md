# Hotel Availability API

Este proyecto es una API en Spring Boot para registrar búsquedas de hoteles.

## Cómo levantarlo con Docker

No necesitas tener nada instalado (ni Java ni Maven), solo Docker.

Desde la raíz del proyecto, ejecuta:
docker compose up --build

Para apagarlo todo:
docker compose down

Si se quiere borrar también los datos de la base de datos:
docker compose down -v

## (Swagger)

http://localhost:8080/swagger-ui.html

Endpoints principales:
- POST /api/v1/search: Registra una búsqueda (devuelve un searchId).
- GET /api/v1/count/{searchId}: Te dice cuántas búsquedas iguales se han hecho.

## Datos de interés

- Stack: Java 21, Spring Boot 3, Kafka, Oracle.

## Desarrollo local
Si prefieres correrlo fuera de Docker, necesitas JDK 21 y tener Oracle/Kafka levantandos y seguir la configuracion del application-local.properties.