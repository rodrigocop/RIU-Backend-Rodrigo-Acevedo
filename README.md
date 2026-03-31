# Hotel — backend API

API REST en Spring Boot para registrar búsquedas de disponibilidad (publicación en Kafka y persistencia en Oracle). Este repositorio se puede levantar **solo con Docker**; la imagen de la aplicación se **compila dentro del Dockerfile** (Maven en contenedor).

## Requisitos

- [Docker](https://docs.docker.com/get-docker/) con soporte para **Compose** (Docker Desktop o el plugin `docker compose` en CLI).

No hace falta tener Java ni Maven instalados en la máquina para el flujo con Compose descrito abajo.

## Levantar el aplicativo con Docker Compose

Desde la raíz del proyecto (donde está `docker-compose.yml`):

1. **Construir imágenes y arrancar todos los servicios** (ZooKeeper, Kafka, Oracle y la aplicación):

   ```bash
   docker compose up --build
   ```

   - La primera vez el build de la app ejecuta `mvn package` dentro del contenedor (puede tardar varios minutos).
   - **Oracle** puede tardar **varios minutos** en quedar listo; el servicio `app` espera a que Kafka y Oracle pasen el healthcheck antes de arrancar.

2. **Comprobar** que la aplicación responde (en otra terminal):

   ```bash
   curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/v3/api-docs
   ```

   Debería devolver `200`.

3. **Detener** los contenedores (mantiene el volumen de datos de Oracle):

   ```bash
   docker compose down
   ```

4. **Detener y borrar volúmenes** (vuelve a aplicar scripts de init de Oracle en el próximo arranque; útil si cambia el esquema SQL):

   ```bash
   docker compose down -v
   ```

### Puertos útiles en el host

| Servicio   | Puerto | Uso |
|-----------|--------|-----|
| Aplicación | **8080** | HTTP / API |
| Kafka     | 9092   | Cliente Kafka desde el host (`PLAINTEXT_HOST`) |
| Oracle    | 1521   | JDBC (p. ej. `XEPDB1`) |

En Compose, la app usa `SYSTEM` / contraseña `oracle` contra el servicio `oracle`, según las variables del propio `docker-compose.yml`.

## Documentación Swagger (OpenAPI)

Con la app en marcha en **localhost** y perfil **prod** de Compose (puerto **8080**):

| Recurso | URL |
|--------|-----|
| **Swagger UI** | [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html) |
| **OpenAPI (JSON)** | [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs) |

Ahí se listan los tags **Disponibilidad hotel** y **Conteo de búsquedas**, con los esquemas de petición y respuesta.

## Endpoints desarrollados

| Método | Ruta | Descripción | Respuestas habituales |
|--------|------|-------------|------------------------|
| `POST` | `/api/v1/search` | Registra una búsqueda: genera `searchId`, envía el evento a Kafka (`hotel_availability_searches`) y responde al instante; la persistencia la hace el consumidor al procesar el mensaje. Cuerpo JSON con `hotelId`, `checkIn` y `checkOut` en formato **`dd/MM/yyyy`**, y arreglo `ages`. | **202** Accepted con `searchId` · **400** validación o JSON ilegible |
| `GET` | `/api/v1/count/{searchId}` | Devuelve la búsqueda asociada al `searchId` y el número de registros con los mismos criterios (hotel, fechas y criterio de edades). | **200** con `search`, `count` y `searchId` · **404** si no existe el id |

## Construir solo la imagen de la aplicación

Sin levantar el resto de la stack:

```bash
docker build -t hotel-app:local --build-arg SKIP_TESTS=true .
```

El JAR se genera en la fase `builder` del `Dockerfile`; `SKIP_TESTS=false` ejecuta `mvn verify` en lugar de `package -DskipTests`.

## Desarrollo local sin Docker

Si dispone de Oracle y Kafka en local, puede ejecutar la app con el perfil `local` y Maven (requiere JDK 21 y Maven en el PATH):

```bash
mvn spring-boot:run
```

Por defecto el proyecto usa `spring.profiles.default=local` (puerto y datasource según `application-local.properties`).
