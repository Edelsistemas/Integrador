# Arquitectura objetivo

## Objetivo

Construir una nueva revisión propia del Integrador, documentada y controlada por specs, manteniendo la base intermedia SQL Server como fuente oficial entre Teamcenter y SAP.

## Principios objetivo

- Sin dependencia funcional ni técnica de BitOne.
- Configuración por ambiente fuera del código fuente.
- Harness mínimo para validar build, ejecución local, reglas de procesamiento y no exposición de secretos.
- Documentación viva en `knowledge/`.
- Toda modificación funcional debe salir de una spec aprobada.

## Componentes que se mantienen

- Teamcenter como origen funcional.
- SQL Server `DB_Edelflex_SAP` como base intermedia.
- SAP B1 Service Layer como destino.
- MongoDB, si se decide conservar como auditoría interna.

## Componentes a eliminar o reemplazar

- `send-process-data-batch` hacia BitOne.
- `BitOneAppender` o cualquier envío externo de logs.
- Credenciales hardcodeadas en YAML versionado.

## Criterios de éxito

- Se puede levantar localmente sin disparar integraciones productivas.
- Se documenta exactamente qué tablas y campos procesa.
- Se validan reglas CREATE/UPDATE/inactivación con tests o casos manuales reproducibles.
- Se puede auditar cada registro procesado sin depender de servicios externos de terceros.
