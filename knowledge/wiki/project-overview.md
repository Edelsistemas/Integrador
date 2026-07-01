# Project overview - Integrador Teamcenter a SAP

## Descripcion

El Integrador es una aplicacion Java / Spring Boot que sincroniza articulos liberados en Siemens Teamcenter PLM hacia SAP Business One usando SAP Service Layer.

## Objetivo funcional

Cuando un articulo queda aprobado en Teamcenter, Teamcenter inserta un registro en una base SQL Server intermedia. El Integrador procesa esos registros y crea o actualiza el maestro de articulos de SAP B1.

## Usuarios o actores

- Ingenieria / producto Edelflex: crean, modifican y aprueban articulos en Teamcenter.
- Descar: responsable de implementacion Teamcenter y escritura de base intermedia.
- Integrador: proceso automatico que lee SQL Server y escribe SAP.
- SAP B1: sistema destino del maestro de articulos.
- MongoDB: auditoria tecnica y trazabilidad del proceso.
- IT Edelflex: operacion, revision y futura evolucion propia.

## Alcance actual

Incluye:

- Lectura de multiples tablas intermedias SQL Server.
- Mapeo de campos por tipo de item.
- Decision CREATE vs UPDATE contra SAP.
- Envio de payload a SAP Service Layer.
- Registro de estado de procesamiento en SQL Server.
- Registro historico en MongoDB.
- Jobs batch programados por perfiles Spring.

Fuera de alcance actual:

- UI funcional de administracion.
- API REST de negocio para operar articulos.
- Correccion automatica de datos mal insertados por Teamcenter.
- Reemplazo de BitOne ya implementado.

## Estado de implementacion

Productivo legacy en servicio. Se recupero el codigo y se preparo una rama `main-GS` para estabilizar ejecucion local, desacoplar perfiles y evitar ejecucion accidental de batches.

## Organizacion actual del repositorio

- Repositorio operativo actual: monorepo raiz `C:\Gustavo\Integrador`.
- La app Java vive en `integration-service/sources/ede-integration-service`.
- `knowledge/` contiene la base documental y el sistema operativo Wiki-SDD.
- `mongodb/` e `integration-service/` conservan la infraestructura local de desarrollo.

## Estado de automatizacion documental

- El bootstrap Wiki-SDD debe reconciliar la estructura existente; no recrearla desde cero.
- Durante bootstrap no se toca codigo productivo.
- Durante bootstrap no se hace commit ni push.

## Fuentes

- Codigo fuente Java/Spring Boot.
- Planillas de mapeo Teamcenter/SAP.
- Correos historicos.
- Contexto aportado por Edelflex.
