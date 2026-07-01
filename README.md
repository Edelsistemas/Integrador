# Integrador

Monorepo raiz preparado para incorporar la base de conocimiento `knowledge/` y continuar la evolucion del Integrador bajo un flujo guiado por specs.

## Alcance de este snapshot inicial

- Preserva la historia del componente Java `ede-integration-service`.
- Incorpora `knowledge/` como material de analisis y preparacion previa al bootstrap Wiki-SDD.
- Versiona la composicion operativa local (`integration-service/`, `mongodb/`) sin incluir datos persistidos ni secretos adicionales.

## Estructura actual

- `integration-service/`: compose y fuentes de la aplicacion.
- `mongodb/`: compose local de MongoDB para desarrollo.
- `knowledge/`: base documental y artefactos SDD en evolucion.

## Nota

Este commit base existe para congelar el estado previo al prompt principal de bootstrap Wiki-SDD.
