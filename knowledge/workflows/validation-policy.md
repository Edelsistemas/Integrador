# Validation policy

## Modos

### DocsOnly

Valida estructura documental y workflow:
- `AGENTS.md`
- `knowledge/`
- workflows minimos
- agentes minimos
- folders de specs
- `workflow-state.md`
- politica de commit
- politica de validacion
- gates definidos
- markdowns criticos no vacios

### Full

Solo con autorizacion explicita. En este repo hoy se detectan como candidatos:
- `mvn clean package -DskipTests`
- `docker compose up -d --build` para stack local
- chequeos documentales y de secretos

## Reglas

- Si no hay autorizacion, no ejecutar Full.
- Si no se detecta un comando, registrarlo como pendiente.
- Si un check critico falla, no se puede completar la tarea.

