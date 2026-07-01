# Relevamiento por conversación - Integrador

## Estado de confianza
Confirmado por Edelflex salvo donde se indique lo contrario.

## Resumen

El Integrador es una aplicación para sincronizar artículos desde Siemens Teamcenter PLM hacia SAP B1 por Service Layer. Teamcenter inserta registros en una base intermedia SQL Server cuando un workflow de autorización finaliza aprobado. El Integrador lee registros pendientes, crea o modifica artículos en SAP y luego actualiza campos de control en la base intermedia para marcar el resultado.

## Confirmaciones del usuario

- El Integrador **crea y modifica** artículos en SAP B1.
- Estado Teamcenter `60`: aprobado / liberado / autorizado para SAP.
- Estado Teamcenter `90`: inactivo / obsoleto / cancelado / congelado.
- La base intermedia está en SQL Server.
- Base detectada: `DB_Edelflex_SAP`.
- La base intermedia fue creada por Descar.
- Teamcenter escribe directamente nuevos registros en la base intermedia.
- Teamcenter no actualiza registros existentes según entendimiento actual; inserta nuevos registros ante workflows correctos.
- El Integrador actualiza campos de proceso en la base intermedia.
- Edelflex probó que al vaciar manualmente `Fecha_Proceso` el Integrador vuelve a procesar el registro y vuelve a escribir fecha.
- La base SQL Server intermedia sigue siendo la fuente oficial entre Teamcenter y SAP.
- El Integrador actual está en servicio, aunque puede sacarse de servicio sin problemas.
- Hay ambiente de testing y ambiente productivo.
- MongoDB, SQL Server y SAP Service Layer siguen activos.
- BitOne participó como subcontratado/desarrollador técnico asociado a 4Points y no debe continuar como dependencia en la nueva revisión.

## Actores

| Actor | Rol |
|---|---|
| Edelflex | Cliente, define criterios, valida pruebas finales |
| 4Points | Responsable contractual / desarrollo del Integrador |
| Bit-One | Subcontratado por 4Points para programación/soporte técnico |
| Descar | Implementador Teamcenter; creador/escritor de base intermedia |
| Techtherapy | Consultoría SAP B1 / Service Layer |

## Personas mencionadas

| Organización / área | Personas |
|---|---|
| 4Points | Juan Manuel Vazquez, Judith Borsato |
| Descar | Alvaro Chany |
| Techtherapy | Guido Garber |
| Edelflex validación | Ana Briano, Lucia Mesch |
| Edelflex IT | Cupaiolo, Gregorio Hakimian |
| Edelflex ingeniería | Parise, Szymkowicz |

## Objetivo futuro

Documentar la versión actual y luego construir una nueva revisión propia, sin dependencia de BitOne y con especificaciones nuevas aprobadas antes de modificar funcionalidad.
