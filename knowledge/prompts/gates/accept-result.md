# accept-result

## Intencion del gate

Aceptar el resultado y sus evidencias.

## Que debe verificar el agente

- estado actual del workflow
- archivos en scope
- riesgos abiertos
- evidencias disponibles
- restricciones de seguridad

## Que puede modificar

- reportes
- handoffs
- workflow-state
- spec en scope si el gate lo permite

## Que no puede modificar

- codigo productivo fuera de scope
- secretos
- estado de una spec sin gate humano correspondiente
- commit o push sin autorizacion explicita

## Reporte requerido

- objetivo
- chequeos realizados
- hallazgos
- decision sugerida
- riesgos
- siguiente gate recomendado

## Evidencia requerida

- referencias a spec
- validacion ejecutada o pendiente
- diff resumido cuando aplique

## Restricciones permanentes

- prohibido commitear salvo HUMAN_GATE: AUTHORIZE_COMMIT
- prohibido hacer push

