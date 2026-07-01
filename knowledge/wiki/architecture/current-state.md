# Arquitectura actual

## Resumen

```text
Teamcenter PLM
  -> SQL Server / DB_Edelflex_SAP / tablas por tipo de item
  -> Integrador Spring Boot / Spring Batch
  -> SAP B1 Service Layer / Items
  -> SQL Server / actualizacion de estado de procesamiento
  -> MongoDB / auditoria tecnica
  -> BitOne / envio externo de trazas en version legacy
```

## Estado reconciliado al 2026-06-30

### Confirmado

- La aplicacion compila y empaqueta con Java 21 y Maven.
- El branch de trabajo actual es `main-GS` y en esta copia local apunta a `c0c9dc5`.
- Los tags `RECOVERY-ORIGINAL` y `DOCKER-STABLE` existen y apuntan a `94c01e9` y `1b2d691` respectivamente.
- La UI minima sigue siendo `GET /` con Basic Auth.
- El endpoint `/` puede devolver `500` en una base Mongo limpia por no tolerar `batchItems = null`.
- El perfil `local` levanta la aplicacion sin jobs batch activos por defecto.
- En este entorno local Windows el stack se esta ejecutando con dos compose separados:
  - `integration-service`
  - `mongodb`
- El Mongo local de desarrollo actual corre en Docker con:
  - puerto host `9001`
  - puerto interno `27017`
  - IP Docker fija `10.15.0.3`
- Los perfiles `local` y `dev` de esta copia del repo fueron ajustados para usar ese Mongo local (`10.15.0.3:27017`).
- El contenedor `integration-service` levanta correctamente en `http://localhost:8080`.
- La respuesta `401 Unauthorized` sin credenciales en `/` confirma que Tomcat, Spring Security y el mapeo Docker estan funcionando.

### Inferido

- `main-GS` es la linea viva de trabajo local y de soporte tecnico, mientras `master` queda como referencia recuperada.
- El montaje de `src/main/resources` como configuracion externa en el compose local es una adaptacion operativa del entorno Windows para evitar rebuilds inconsistentes mientras no haya wrapper o pipeline mas formal.
- La topologia local actual replica de forma suficiente el layout operativo de produccion para debugging, aunque no necesariamente todos los secretos, IPs y automatismos.

### Dudoso

- La version exacta que hoy corre en produccion respecto de `master`, `main-GS` y `DOCKER-STABLE`.
- Si la query real de pendientes en produccion depende solo de `Status`, solo de `Fecha_Proceso`, o de una combinacion no documentada.
- Si la regla de crear articulos nuevos como inactivos sigue siendo una decision funcional vigente o un efecto lateral del codigo legacy.

### Pendiente

- Corregir la pantalla `status.html` para soportar estado vacio sin romper con `500`.
- Decidir si el compose local debe seguir usando configuracion externa montada o si el flujo oficial sera siempre `mvn clean package` previo a `docker compose up --build`.
- Definir el reemplazo de BitOne y su contrato tecnico.
- Externalizar secretos y credenciales fuera del repositorio.

## Tecnologias confirmadas

| Componente | Tecnologia | Estado |
|---|---|---|
| Aplicacion | Java 21, Spring Boot 3.4.0 | Confirmado |
| Batch | Spring Batch | Confirmado |
| Build | Maven | Confirmado |
| Contenedor | Docker / Docker Compose | Confirmado |
| Base intermedia | SQL Server | Confirmado |
| Auditoria | MongoDB | Confirmado |
| SAP | SAP Business One Service Layer | Confirmado |
| Seguridad UI minima | Basic Auth para `/` | Confirmado |

## Jobs existentes

| Job | Proposito | Frecuencia observada | Estado de certeza |
|---|---|---|---|
| `sync-items-batch` | Procesa articulos Teamcenter -> SAP | cada 2 minutos segun `batch-process.sync-business-partners` | Confirmado |
| `send-process-data-batch` | Envia trazas de Mongo/BatchProcess a endpoint BitOne | cada 10 segundos | Confirmado |
| `clean-up-process-batch` | Limpia historico Mongo `items-process-history` | diario 23:00 | Confirmado |

## Topologia local observada

```text
Windows 11 host
  -> Docker Desktop / network localdocker (10.15.0.0/24)
     -> mongodb-mongo-1 (10.15.0.3:27017, expuesto como localhost:9001)
     -> integration-service (10.15.0.2:8095, expuesto como localhost:8080)
```

### Detalles locales confirmados

- `integration-service/docker-compose.yml` usa:
  - `SPRING_PROFILES_ACTIVE=local`
  - `SPRING_CONFIG_ADDITIONAL_LOCATION=file:/config/`
  - `./sources/ede-integration-service/src/main/resources:/config:ro`
- `mongodb/docker-compose.yml` usa volumen local `./data:/data/db`.
- La base activa local de Mongo para esta copia de desarrollo es `mongodb/data`.
- El backup historico `mongodb/data-backup-2026-06-30-1629` fue marcado para retiro del arbol del proyecto y resguardo externo, dado que esta copia prioriza continuidad de desarrollo y no conservacion historica local.

## Archivos tecnicos centrales

| Archivo | Rol | Estado |
|---|---|---|
| `application.yml` | Define query SQL, update SQL y mapeo por tabla | Confirmado |
| `application-local.yml` | Configuracion local, hoy apuntando a Mongo local Docker | Confirmado |
| `application-dev.yml` | Configuracion dev, hoy apuntando a Mongo local Docker | Confirmado |
| `application-prod.yml` | Parametros productivos, incluyendo SAP, Mongo, SQL y grupos SAP | Confirmado |
| `SQLServerService.java` | Construye SELECT dinamico, lee SQL Server y actualiza campos de proceso | Confirmado |
| `SyncBaseProductReader.java` | Lee registros pendientes por tabla | Confirmado |
| `SyncBaseProductProcessor.java` | Decide CREATE/UPDATE y llama SAP | Confirmado |
| `Product.java` | Construye payload SAP y evalua inactivacion | Confirmado |
| `SapItemService.java` | Llama SAP Service Layer | Confirmado |
| `SyncBaseProductWriter.java` | Actualiza SQL y registra historico Mongo | Confirmado |
| `SendProcessDataTasklet.java` | Envia trazas a BitOne | Confirmado |
| `BitOneAppender.java` | Appender logback para envio externo de logs | Confirmado |
| `InfoController.java` | Renderiza la pantalla `/` y hoy no tolera base limpia | Confirmado |
| `status.html` | Plantilla Thymeleaf del estado, hoy asume `batchItems != null` | Confirmado |

## Nota operativa local

- Al 2026-07-01 se acepta conservar fuera del proyecto el backup `data-backup-2026-06-30-1629`, siempre que quede preservado en un resguardo externo.
- Para el trabajo diario local, la unica carpeta de datos activa requerida es `mongodb/data`.

## Limitaciones actuales

- Credenciales y endpoints siguen presentes en YAML y codigo; deben externalizarse.
- BitOne sigue apareciendo como dependencia tecnica legacy.
- La pantalla `/` no soporta el estado sin ejecuciones previas y falla con `500`.
- La query base de pendientes sigue necesitando validacion contra la implementacion productiva exacta.
- No hay tests automatizados documentados para las reglas criticas de inactivacion ni para la UI minima.
- La advertencia de seguridad por `User.withDefaultPasswordEncoder()` sigue presente en logs.

