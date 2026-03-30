# Hotel Backend - Arquitectura Hexagonal

## Estructura de módulos y paquetes (hexagonal clásica)

- `domain` (solo Java, sin Spring):
  - `com.riu.hotel.domain` — modelo de dominio (lógica pura).
  - `com.riu.hotel.port.in` — puertos de entrada (casos de uso).
  - `com.riu.hotel.port.out` — puertos de salida (repositorios, mensajería, APIs externas).
- `application`: implementaciones de los casos de uso (`com.riu.hotel.application`).
- `infrastructure`: adaptadores concretos (`com.riu.hotel.adapter.*` — Kafka, Oracle, configuración técnica).
- `bootstrap`: arranque Spring Boot y composición (`com.riu.hotel.bootstrap`), más adaptador HTTP en `com.riu.hotel.adapter.in.web`.

## Flujo funcional

1. `POST /api/v1/hotel/availability-searches` recibe la solicitud.
2. El caso de uso publica el evento en Kafka (`hotel_availability_searches`).
3. El consumer de Kafka procesa el evento.
4. El caso de uso de registro persiste la búsqueda en Oracle.

## Ejecutar local sin Docker

```bash
mvn -Dmaven.repo.local=.m2 test
mvn -pl bootstrap -am spring-boot:run
```

## Ejecutar con Docker

```bash
docker compose up --build
```

Servicios levantados:
- App: `http://localhost:8080`
- Kafka: `localhost:9092`
- Oracle XE: `localhost:1521`

## Configuración relevante

- Topic Kafka: `hotel_availability_searches`
- Propiedades en `bootstrap/src/main/resources`:
  - `application.properties` (común + perfil por defecto `local`)
  - `application-local.properties`
  - `application-prod.properties` (activar con `SPRING_PROFILES_ACTIVE=prod`)
- Script SQL inicial Oracle: `docker/oracle/init/01-create-hotel-availability-searches.sql`

### Perfiles

- **Local**: por defecto (`spring.profiles.default=local`) o `SPRING_PROFILES_ACTIVE=local`
- **Producción**: `SPRING_PROFILES_ACTIVE=prod` y variables `ORACLE_*`, `KAFKA_BOOTSTRAP_SERVERS`

