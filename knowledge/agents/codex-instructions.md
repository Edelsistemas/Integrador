# Codex Instructions

## Antes de modificar código

1. Leer `knowledge/wiki/index.md`.
2. Leer `knowledge/wiki/project-overview.md`.
3. Leer la spec aprobada correspondiente.
4. Leer `human-review.md` de la spec.
5. Identificar archivos afectados.
6. Proponer plan breve.

## Durante el cambio

- Hacer cambios pequeños.
- Mantener consistencia con arquitectura actual.
- No hardcodear secretos.
- No activar perfiles productivos.
- No introducir dependencia BitOne.

## Después del cambio

- Ejecutar harness.
- Documentar comandos y resultados.
- Actualizar `implementation-notes.md`.
- Actualizar `traceability.md`.
- Generar handoff.

## Formato obligatorio para comandos en documentación

Para cada paso documentar:

- Qué comando ejecutar.
- Desde qué carpeta.
- Qué debería pasar.
- Qué significa cada resultado.
- Qué hacer si falla.
- Por qué se hace el paso.
