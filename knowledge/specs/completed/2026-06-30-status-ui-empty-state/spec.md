# Spec: Tolerancia de pantalla de estado sin ejecuciones previas

## Estado
Completed

## Objetivo

Evitar que `GET /` falle con `500` cuando MongoDB no tiene ningun registro de `BatchProcess`.

## Problema que resuelve

En una instalacion limpia o una base Mongo nueva, la UI minima de estado intenta renderizar `batchItems.dateStartStr` aun cuando `batchItems` es `null`, lo que provoca error de Thymeleaf y pagina `Whitelabel Error Page`.

## Alcance incluido

- Manejo seguro del caso `batchItems == null`.
- Visualizacion de estado vacio amigable.
- Correccion del form de refresco para que apunte al endpoint real si corresponde.

## Fuera de alcance

- Reemplazo de BitOne.
- Cambios de seguridad.
- Cambios funcionales en los batches.
- Siembra de datos dummy como solucion permanente.

## Fuentes

- Codigo fuente actual (`InfoController.java`, `status.html`).
- Prueba local del 2026-06-30 con Mongo limpio.
- Logs del contenedor `integration-service`.


