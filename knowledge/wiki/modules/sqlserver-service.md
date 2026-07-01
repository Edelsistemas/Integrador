# Módulo: SQLServerService

## Propósito

Leer y actualizar la base intermedia SQL Server.

## Responsabilidades

- Construir queries SELECT dinámicos reemplazando `FIELDS` y `TABLE`.
- Convertir columnas SQL a propiedades Java `Product`.
- Actualizar campos de resultado del procesamiento.

## Puntos técnicos

- Los campos `FIXED_*` se convierten en valores constantes del SELECT.
- Los campos `CLAUSE_*` se convierten en expresiones SQL calculadas.
- El update escribe `Status`, `Fecha_Proceso`, `Mensaje_Proceso`, `Id_Proceso`.

## Riesgos

- No parametriza el nombre de tabla/campos porque salen de configuración interna. Si esa configuración queda expuesta o editable sin control, hay riesgo SQL.
- El criterio de pendiente en código (`Status IS NULL OR Status = 'ERROR'`) debe validarse contra operación real basada en `Fecha_Proceso`.

## Estado
Confirmado por código.
