# Design - As-is

## Componentes afectados

- `SyncItemsLauncher`
- `SyncItemsConfig`
- `SyncBaseProductReader`
- `SyncBaseProductProcessor`
- `SyncBaseProductWriter`
- `SQLServerService`
- `Product`
- `SapItemService`
- `ProcessService`

## Flujo

1. Launcher ejecuta job.
2. Config crea step por tabla.
3. Reader carga lista desde SQL.
4. Processor decide CREATE/UPDATE.
5. Product arma request SAP.
6. SapItemService ejecuta llamada.
7. Writer actualiza SQL y Mongo.

## Manejo de errores

- Errores SAP se capturan y se guardan como `ProductProcessInfo.Status.ERROR`.
- Registros con error pueden volver a ser leídos si `Status = ERROR` según query base.
- Errores de writer se registran como métrica, pero deben revisarse para asegurar no pérdida de consistencia.
