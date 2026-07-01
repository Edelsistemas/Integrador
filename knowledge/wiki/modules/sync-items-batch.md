# Módulo: sync-items-batch

## Propósito

Procesar registros pendientes de tablas intermedias SQL Server y sincronizarlos con SAP B1.

## Responsabilidades

- Recorrer la lista de configuraciones por tipo de producto.
- Crear un step por tabla.
- Leer registros pendientes.
- Decidir CREATE/UPDATE.
- Enviar a SAP.
- Actualizar SQL Server.
- Registrar histórico en MongoDB.

## Entradas

- `team-center.querys.configs` desde `application.yml`.
- Tablas SQL Server en `DB_Edelflex_SAP`.

## Salidas

- `Items` en SAP B1.
- Campos `Status`, `Fecha_Proceso`, `Mensaje_Proceso`, `Id_Proceso` en SQL Server.
- Documentos en MongoDB `items-process-history`.

## Estado

Confirmado por código.
