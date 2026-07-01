# Índice de conocimiento del proyecto

## Estado general

- Estado actual: aplicación legacy productiva en servicio.
- Último hito conocido: recuperación del código fuente, análisis local en VSCode/Docker y documentación inicial.
- Próximo hito: completar revisión propia documentada y preparar specs de cambios funcionales futuros.
- Nivel de confianza del conocimiento: alto para comportamiento observado en código; medio para intención funcional histórica hasta completar contraste exhaustivo con todos los correos.

## Entradas principales

- [Project overview](project-overview.md)
- [Arquitectura actual](architecture/current-state.md)
- [Arquitectura objetivo](architecture/target-state.md)
- [Contexto del sistema](architecture/system-context.md)
- [Flujo de datos](architecture/data-flow.md)
- [Mapa de integración](architecture/integration-map.md)
- [Registro de decisiones](decisions/decision-log.md)
- [Registro de riesgos](risks/risk-register.md)
- [Glosario](glossary.md)
- [Preguntas abiertas](open-questions.md)

## Specs activas

- Draft: ninguna todavía.
- Approved: ninguna todavía.
- Completed: `specs/completed/2026-06-29-integrador-teamcenter-sap-current-behavior/`.

## Pendientes críticos

- Confirmar en código productivo real si los cambios locales `main-GS` ya fueron aplicados o solo están en rama de trabajo.
- Validar si el criterio real de pendiente debe ser `Fecha_Proceso` vacía, `Status IS NULL`, `Status = ERROR`, o combinación.
- Decidir nueva versión propia sin dependencia BitOne.
- Revisar exposición de secretos en YAML antes de cualquier publicación fuera de entorno controlado.
