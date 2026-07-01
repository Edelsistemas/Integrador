# Data Model - As-is

## SQL Server intermedio

Base: `DB_Edelflex_SAP`.

Campos de control actualizados por Integrador:

| Campo | Uso |
|---|---|
| `Status` | Resultado `OK`/`ERROR` o pendiente si NULL según código |
| `Fecha_Proceso` | Fecha en que el Integrador procesó |
| `Mensaje_Proceso` | Respuesta/error serializado |
| `Id_Proceso` | Identificador de ejecución |

## MongoDB

Colecciones:

- `items-process-history`
- `batch-process`

## SAP Items

Ver `outputs/documentation/mapa-campos-teamcenter-sql-sap.md`.
