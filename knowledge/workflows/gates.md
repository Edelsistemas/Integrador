# Gates

## Gates humanos obligatorios

- `HUMAN_GATE: APPROVE_SPEC`
- `HUMAN_GATE: REQUEST_SPEC_CHANGES`
- `HUMAN_GATE: START_IMPLEMENTATION`
- `HUMAN_GATE: REQUEST_VALIDATION`
- `HUMAN_GATE: ACCEPT_RESULT`
- `HUMAN_GATE: REJECT_RESULT`
- `HUMAN_GATE: COMPLETE_SPEC`
- `HUMAN_GATE: AUTHORIZE_COMMIT`

## Reglas

- Un agente no puede aprobar su propia spec.
- Un agente no puede implementar una spec en `Draft`.
- Un agente no puede declarar `Completed` sin aceptacion humana.
- Un agente no puede commitear sin `AUTHORIZE_COMMIT`.
- Un agente no puede pushear salvo autorizacion explicita.
- Si faltan evidencias, no se puede completar.
- Si hay dudas semanticas, se debe escalar a decision humana antes de implementar.

