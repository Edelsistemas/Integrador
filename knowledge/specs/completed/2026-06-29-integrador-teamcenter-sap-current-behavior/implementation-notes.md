# Implementation Notes

## Archivos modificados en recuperación local

- `CleanUpProcessLauncher.java`: se propuso/agregó `@Profile("clean-up-process-batch")`.
- `SendProcessDataLauncher.java`: se propuso/agregó `@Profile("send-process-data-batch")`.
- `SyncItemsLauncher.java`: se propuso/agregó `@Profile("sync-items-batch")`.
- `application-local.yml`: ajustes para entorno local.
- `application-prod.yml`: revisión de configuración.

## Git

- Rama original: `master`.
- Rama de trabajo propia: `main-GS`.
- Tag esperado original: `RECOVERY-ORIGINAL` -> commit original.
- Tag esperado estable: `DOCKER-STABLE` -> commit con Docker local estable.

## Pendiente

Verificar que los tags apunten a los commits correctos en GitHub.
