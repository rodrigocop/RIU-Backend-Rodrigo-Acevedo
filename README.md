# Hotel — backend API

API REST en Spring Boot 3 (Java 21) para registrar búsquedas de disponibilidad: el `POST` publica en Kafka y el consumidor persiste en Oracle. El repositorio incluye `docker-compose.yml` para levantar ZooKeeper, Kafka, Oracle y la aplicación sin instalar Maven ni JDK en el host (la imagen de la app compila con Maven dentro del Dockerfile).

**Requisitos:** Docker con soporte para Compose.

**Arranque:** desde la raíz del proyecto, `docker compose up --build`. La primera ejecución puede tardar (build Maven y arranque de Oracle). La app expone HTTP en **8080** cuando los healthchecks de Kafka y Oracle pasan. Para comprobar: `curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/v3/api-docs` → `200`.

**Parar:** `docker compose down` (conserva el volumen de Oracle). Con `docker compose down -v` se borran volúmenes y en el próximo arranque se vuelven a aplicar los scripts bajo `docker/oracle/init/`.

En Compose, Swagger UI está en [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) y el OpenAPI JSON en `/v3/api-docs`. Kafka escucha en el host en **9092** (host `PLAINTEXT_HOST`); Oracle JDBC en **1521** (`XEPDB1`), según variables de `docker-compose.yml`.

**Endpoints principales:** `POST /api/v1/search` — registra la búsqueda (validación del cuerpo, fechas `dd/MM/yyyy`, respuesta **202** con `searchId`). `GET /api/v1/count/{searchId}` — snapshot de la búsqueda y número de registros con los mismos criterios (**200** / **404**).

**Solo imagen de la app:** `docker build -t hotel-app:local --build-arg SKIP_TESTS=true .`

**Desarrollo local:** con JDK 21 y Maven, `mvn spring-boot:run` (perfil por defecto `local` en `application-local.properties`).

**Tests y cobertura:** JaCoCo está en el `pom.xml`. Tras `mvn test` o `mvn verify`, el informe HTML está en `target/site/jacoco/index.html` y el XML en `target/site/jacoco/jacoco.xml`. `mvn verify` aplica también el umbral mínimo de líneas configurado e imprime la ruta del informe. Sin ejecutar tests no se actualiza la cobertura.

La aplicación usa hilos virtuales de plataforma (`spring.threads.virtual.enabled=true`) cuando el runtime lo soporta.
