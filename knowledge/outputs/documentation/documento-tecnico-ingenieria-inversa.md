# Documento técnico de ingeniería inversa - Integrador Teamcenter/SAP

## 1. Objetivo

Documentar cómo funciona actualmente el Integrador, con foco en código fuente, configuración, flujo de datos y riesgos.

## 2. Stack técnico

- Java 21.
- Spring Boot 3.4.0.
- Spring Batch.
- Maven.
- Docker.
- SQL Server.
- MongoDB.
- SAP B1 Service Layer.

## 3. Estructura de ejecución

El proyecto real Maven está en:

```text
integration-service/sources/ede-integration-service
```

La raíz Docker usada en el entorno local está en:

```text
integration-service
```

## 4. Flujo batch principal

### Paso 1 - Launcher

`SyncItemsLauncher` ejecuta periódicamente el job `SYNC_ITEMS` usando `@Scheduled`.

### Paso 2 - Configuración de steps

`SyncItemsConfig` crea un step por cada tabla definida en `team-center.querys.configs`.

### Paso 3 - Reader

`SyncBaseProductReader` llama a `SQLServerService.getProducts()`.

### Paso 4 - Query SQL

La query base en configuración es:

```sql
SELECT FIELDS FROM DB_Edelflex_SAP.dbo.TABLE
WHERE Status IS NULL OR Status = 'ERROR'
ORDER BY id
```

### Paso 5 - Processor

`SyncBaseProductProcessor` consulta SAP:

- Si el artículo existe: acción `UPDATE`.
- Si no existe: acción `CREATE`.

### Paso 6 - Reglas de inactivación

`Product.evaluateInactivo()` marca inactivo si cambia:

- `ItemName`.
- `ItemsGroupCode`.
- `Properties1` / importado.
- `Properties2` / fabricado.
- O si estado Teamcenter es `90`.

Además, en creación, el código establece `inactivo=true`.

### Paso 7 - Request SAP

`Product.createRequest()` arma el payload con:

- `ItemName`
- `ItemsGroupCode`
- `InventoryUOM`
- `U_SEIDORAR_REVISION`
- `U_SEIDORAR_ESTADO`
- `U_SEIDORAR_ARTICULO_EDE_2`
- UDFs `U_SEI_*`
- `Properties1` y `Properties2`
- `Valid`/`Frozen` si corresponde inactivar.

### Paso 8 - Writer

`SyncBaseProductWriter` actualiza SQL Server y guarda histórico en MongoDB.

## 5. Comandos operativos básicos

### Compilar

- Carpeta: `C:\Gustavo\integration-service\sources\ede-integration-service`
- Comando:

```powershell
mvn clean package
```

- Resultado esperado: `BUILD SUCCESS`.
- Si falla por PKIX: importar certificado corporativo al cacerts de Java 21.

### Levantar Docker local

- Carpeta: `C:\Gustavo\integration-service`
- Comando:

```powershell
docker compose up --build
```

- Resultado esperado: Spring Boot inicia en puerto interno 8095 y host 8080.
- Si falla por red `localdocker`: crear red con `docker network create --subnet=10.15.0.0/24 localdocker`.

### Ver ramas y tags

- Carpeta: repo Maven.
- Comando:

```powershell
git log --oneline --graph --decorate --all
```

- Si la terminal no devuelve prompt, presionar `q` porque Git abrió el paginador.

## 6. Advertencias

- No activar perfiles batch contra producción desde entorno local.
- No publicar YAML con credenciales reales.
- No asumir que `master` contiene cambios de estabilización local; esos están en `main-GS`.
