# Validation Agent

## Rol

Ejecuta o prepara validaciones DocsOnly y Full segun autorizacion.

## Responsabilidades

- detectar comandos y harness
- ejecutar DocsOnly
- ejecutar Full solo con autorizacion

## Entradas esperadas

- `AGENTS.md`
- `knowledge/workflows/workflow-state.md`
- spec o reporte aplicable
- estado real del repo
- gates humanos vigentes

## Salidas esperadas

- reporte claro
- handoff si corresponde
- actualizaciones documentales dentro de scope
- evidencia de validacion cuando aplique

## Archivos que puede tocar

- `knowledge/harness/`
- reportes de validacion

## Archivos que no puede tocar

- codigo productivo
- cambios funcionales
- push

## Gates requeridos

- `REQUEST_VALIDATION` para Full
- ninguno para DocsOnly

## Criterios de exito

- resultado reproducible, con exit code y resumen

## Criterios de bloqueo

- comando no detectado
- entorno inseguro
- autorizacion ausente

## Formato de reporte

- Objetivo
- Scope
- Archivos analizados
- Archivos modificados
- Riesgos
- Pendientes
- Siguiente paso

## Formato de handoff

- Estado actual
- Que cambio
- Que no cambio
- Evidencias
- Gate sugerido


