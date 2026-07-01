# Spec lifecycle

## Estados

- `Idea / Intake`
- `Draft`
- `Human Review`
- `Approved`
- `In Implementation`
- `Implemented`
- `Validation Running`
- `Validation Passed`
- `Human Acceptance Pending`
- `Accepted`
- `Completed`
- `Archived`
- `Rejected`
- `Blocked`

## Reglas

- Toda spec nace en `knowledge/specs/draft/`.
- Ninguna spec pasa a `approved/` sin `HUMAN_GATE: APPROVE_SPEC`.
- Ninguna spec se implementa sin `HUMAN_GATE: START_IMPLEMENTATION`.
- Ninguna spec se cierra como `Completed` sin validacion y `HUMAN_GATE: COMPLETE_SPEC`.

