# ADR-0002 - Remover dependencia BitOne en nueva revisión

## Estado
Draft para nueva revisión

## Contexto

El Integrador legacy contiene envío de trazas y logs hacia infraestructura BitOne. Edelflex indicó que BitOne fue subcontratado asociado a 4Points y que no debe seguir como dependencia.

## Decisión

La nueva revisión propia no debe contemplar BitOne como destino de logs, trazas o procesos.

## Consecuencias positivas

- Reduce dependencia externa.
- Evita salida innecesaria de información técnica.
- Simplifica operación interna.

## Consecuencias negativas

- Si BitOne tenía datos históricos útiles, deberían exportarse antes de retirar acceso.
- Se debe definir auditoría interna mínima equivalente si se necesita soporte.

## Evidencia / fuente

Código (`SendProcessDataTasklet`, `BitOneAppender`) y confirmación Edelflex.
