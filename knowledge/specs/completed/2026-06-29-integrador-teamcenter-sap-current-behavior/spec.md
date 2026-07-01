# Spec: Comportamiento actual del Integrador Teamcenter - SAP

## Estado
Completed / As-is documentado

## Objetivo

Documentar el comportamiento actualmente implementado del Integrador legacy.

## Problema que resuelve

Permite crear y modificar artículos en SAP B1 a partir de registros generados por Teamcenter en SQL Server intermedio.

## Alcance incluido

- Lectura de registros pendientes.
- CREATE/UPDATE de artículos SAP.
- Reglas de inactivación.
- Actualización de estado de proceso.
- Auditoría MongoDB.
- Dependencia BitOne legacy documentada.

## Fuera de alcance

- Nueva implementación.
- Reemplazo BitOne.
- UI de administración.
- Corrección de datos Teamcenter.

## Fuentes

Código fuente, planillas de mapeo, correos y confirmación Edelflex.
