# Implementation lifecycle

## Flujo

1. Reconciliar conocimiento con repo real.
2. Redactar o actualizar spec.
3. Obtener `APPROVE_SPEC`.
4. Obtener `START_IMPLEMENTATION`.
5. Implementar solo archivos en scope.
6. Ejecutar validacion autorizada.
7. Adjuntar evidencias.
8. Obtener `ACCEPT_RESULT` o `REJECT_RESULT`.
9. Obtener `AUTHORIZE_COMMIT` si corresponde.

## Reglas de bootstrap

- Durante bootstrap no hay implementacion funcional.
- Durante bootstrap solo se permite documentacion y harness DocsOnly.
- Si se detecta necesidad de cambios de codigo, se registra una spec draft y se frena alli.

