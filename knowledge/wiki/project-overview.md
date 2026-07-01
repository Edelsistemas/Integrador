# Project overview - Integrador Teamcenter a SAP

## Descripción

El Integrador es una aplicación Java / Spring Boot que sincroniza artículos liberados en Siemens Teamcenter PLM hacia SAP Business One usando SAP Service Layer.

## Objetivo funcional

Cuando un artículo queda aprobado en Teamcenter, Teamcenter inserta un registro en una base SQL Server intermedia. El Integrador procesa esos registros y crea o actualiza el maestro de artículos de SAP B1.

## Usuarios o actores

- Ingeniería / producto Edelflex: crean, modifican y aprueban artículos en Teamcenter.
- Descar: responsable de implementación Teamcenter y escritura de base intermedia.
- Integrador: proceso automático que lee SQL Server y escribe SAP.
- SAP B1: sistema destino del maestro de artículos.
- MongoDB: auditoría técnica y trazabilidad del proceso.
- IT Edelflex: operación, revisión y futura evolución propia.

## Alcance actual

Incluye:

- Lectura de múltiples tablas intermedias SQL Server.
- Mapeo de campos por tipo de ítem.
- Decisión CREATE vs UPDATE contra SAP.
- Envío de payload a SAP Service Layer.
- Registro de estado de procesamiento en SQL Server.
- Registro histórico en MongoDB.
- Jobs batch programados por perfiles Spring.

Fuera de alcance actual:

- UI funcional de administración.
- API REST de negocio para operar artículos.
- Corrección automática de datos mal insertados por Teamcenter.
- Reemplazo de BitOne ya implementado.

## Estado de implementación

Productivo legacy en servicio. Se recuperó el código y se preparó una rama `main-GS` para estabilizar ejecución local, desacoplar perfiles y evitar ejecución accidental de batches.

## Fuentes

- Código fuente Java/Spring Boot.
- Planillas de mapeo Teamcenter/SAP.
- Correos históricos.
- Contexto aportado por Edelflex.
