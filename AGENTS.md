# AGENTS

Este archivo enruta el trabajo operativo del monorepo `Integrador`.

## Fuente de verdad

- Documentacion operativa y tecnica: `knowledge/`
- Estado de workflow actual: `knowledge/workflows/workflow-state.md`
- Specs: `knowledge/specs/`
- Agentes: `knowledge/agents/`
- Harness: `knowledge/harness/`
- Restricciones de seguridad: `knowledge/workflows/safety-constraints.md`

## Reglas de operacion

- Este bootstrap trabaja sobre el monorepo raiz `Integrador`.
- `knowledge/` ya existe y debe reconciliarse, no recrearse desde cero.
- No tocar codigo productivo sin `HUMAN_GATE: START_IMPLEMENTATION`.
- No mover specs entre estados sin gate humano correspondiente.
- No hacer commit sin `HUMAN_GATE: AUTHORIZE_COMMIT`.
- No hacer push sin autorizacion explicita.
- No declarar trabajo `Completed` sin evidencia y aceptacion humana.
- Si hay contradicciones entre repo y documentacion, registrarlas y proponer resolucion.
- Si falta contexto, dejarlo como `Pendiente confirmar` y pedir gate humano antes de asumir.

