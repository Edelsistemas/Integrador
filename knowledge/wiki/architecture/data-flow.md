# Data flow

## Flujo principal

1. Usuario crea/modifica artículo en Teamcenter.
2. Workflow Teamcenter aprueba el artículo.
3. Teamcenter exporta/inserta un registro nuevo en SQL Server intermedio.
4. El Integrador lee registros pendientes.
5. El Integrador consulta SAP para saber si el `ItemCode` existe.
6. Si no existe, ejecuta `POST /b1s/v1/Items`.
7. Si existe, ejecuta `PATCH /b1s/v1/Items('<ItemCode>')`.
8. El Integrador actualiza SQL Server con estado de proceso, fecha, mensaje e ID de proceso.
9. El Integrador registra detalle en MongoDB.

## Campos de control SQL según código

Query base en `application.yml`:

```sql
SELECT FIELDS
FROM DB_Edelflex_SAP.dbo.TABLE
WHERE Status IS NULL OR Status = 'ERROR'
ORDER BY id
```

Update base en `application.yml`:

```sql
UPDATE DB_Edelflex_SAP.dbo.TABLE
SET Status = ?, Fecha_Proceso = ?, Mensaje_Proceso = ?, Id_Proceso = ?
WHERE id = ?
```

## Observación importante

Edelflex confirmó por prueba manual que vaciar `Fecha_Proceso` dispara reproceso. Esto debe contrastarse con la versión exacta del código en producción, porque el `application.yml` analizado filtra explícitamente por `Status` y no por `Fecha_Proceso`.

Posibles explicaciones a validar:

- El código productivo tiene query diferente al backup analizado.
- Existe trigger/vista/proceso SQL que sincroniza `Fecha_Proceso` y `Status`.
- La prueba manual incluyó indirectamente otro cambio de campo.
- Hay configuración externa que sobreescribe `team-center.querys.get`.
