# Specs

Las specs gobiernan cambios funcionales, tecnicos y operativos.

## Estados

- `draft`: propuesta no aprobada.
- `approved`: aprobada por humano.
- `completed`: implementada, validada y aceptada.
- `archived`: material cerrado o reemplazado que se conserva por trazabilidad.

## Reglas

- Ninguna spec `Draft` puede implementarse sin `HUMAN_GATE: APPROVE_SPEC` y `HUMAN_GATE: START_IMPLEMENTATION`.
- Toda spec debe explicitar alcance, no objetivos, validacion y rollback.
- Durante bootstrap se pueden crear o ajustar specs documentales, pero no implementar cambios funcionales.

