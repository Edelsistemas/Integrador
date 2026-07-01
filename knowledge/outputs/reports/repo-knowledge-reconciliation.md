# Report

## Objetivo

Reconciliar `Knowledge/` contra el estado real del repositorio y del entorno local observado al 2026-06-30, sin modificar codigo de la aplicacion.

## Contexto

Se revisaron especialmente:

- `knowledge/wiki/index.md`
- `knowledge/wiki/project-overview.md`
- `knowledge/wiki/architecture/current-state.md`
- `knowledge/wiki/decisions/decision-log.md`
- `knowledge/specs/`
- estructura real del repo en `C:\Gustavo\Integrador\integration-service`
- compose local de `integration-service` y `mongodb`
- logs y comportamiento real del stack local levantado

## Hallazgos

### Confirmado

- La wiki describe correctamente el proposito general del integrador: SQL Server/Teamcenter -> SAP -> auditoria Mongo.
- El codigo real sigue siendo Spring Boot 3.4 / Java 21 / Spring Batch / MongoDB / SQL Server / SAP Service Layer.
- `main-GS` es la rama de trabajo actual y localmente apunta a `c0c9dc5`.
- Los tags `RECOVERY-ORIGINAL` y `DOCKER-STABLE` existen y siguen apuntando a `94c01e9` y `1b2d691`.
- La app local levanta en Docker con perfil `local`, expuesta en `http://localhost:8080`.
- Mongo local levanta en Docker y la app se conecta correctamente a `10.15.0.3:27017`.
- `GET /` sin credenciales responde `401`, lo que confirma seguridad y mapeo HTTP.

### Inferido

- La rama `master` sigue cumpliendo el rol de referencia recuperada y `main-GS` el rol de continuidad operativa local.
- El ajuste de `SPRING_CONFIG_ADDITIONAL_LOCATION=file:/config/` en el compose local es una ayuda de entorno, no necesariamente una decision definitiva de arquitectura.

### Dudoso

- La correspondencia exacta entre la version productiva y el estado actual de `main-GS`.
- El criterio productivo real de seleccion de pendientes por `Status` y/o `Fecha_Proceso`.
- Si la creacion de articulos nuevos como inactivos es regla funcional vigente o legado accidental.

### Desalineaciones detectadas

- `knowledge/wiki/architecture/current-state.md` no reflejaba el estado local actual del stack Docker en `C:\Gustavo\Integrador`.
- `knowledge/wiki/open-questions.md` seguia tratando como abierta la validacion de tags/rama que hoy ya pudo confirmarse.
- La documentacion no explicitaba el bug actual de la UI `/` sobre Mongo vacio.
- El `README.md` del repo documenta bien el flujo general, pero fija `main-GS` en `1b2d691`, mientras el head local actual es `c0c9dc5`.

## Decisiones

- Se actualizo `knowledge/wiki/architecture/current-state.md` con clasificacion por:
  - Confirmado
  - Inferido
  - Dudoso
  - Pendiente
- Se actualizo `knowledge/wiki/open-questions.md` separando lo ya confirmado de lo realmente abierto.
- Se registro una spec draft para el fix posterior de la pantalla `/` sin ejecuciones previas:
  - `knowledge/specs/draft/2026-06-30-status-ui-empty-state/`

## Recomendaciones

- Corregir la pantalla `/` para soportar base Mongo limpia sin depender de registros dummy.
- Actualizar en el futuro `decision-log.md` para dejar constancia de que `DOCKER-STABLE` y `RECOVERY-ORIGINAL` ya fueron verificados en la practica.
- Revisar el `README.md` del repo para evitar que el hash documentado de `main-GS` quede envejecido frente a nuevos commits.
- Definir si el uso de configuracion externa montada en Docker local queda como practica oficial o solo como workaround temporal.

## Riesgos

- El conocimiento funcional historico sigue siendo parcial en lo relativo a pendientes SQL y reglas exactas de negocio.
- La UI minima puede dar una falsa sensacion de estabilidad si se la destraba con datos dummy en lugar de corregir el template.
- El entorno local sigue conteniendo secretos reales en YAML, lo que mantiene riesgo de exposicion y deriva de configuraciones.

## Proximos pasos

1. Si se quiere solo validar la UI rapido, sembrar un registro dummy en Mongo como medida temporal.
2. Implementar luego la spec draft de tolerancia a estado vacio.
3. Reconciliar despues `decision-log.md` y, si se desea, `project-overview.md` con el estado Docker local ya confirmado.

## Fuentes

- Wiki y specs en `C:\Gustavo\Integrador\knowledge`
- Repo real en `C:\Gustavo\Integrador\integration-service\sources\ede-integration-service`
- Compose local en:
  - `C:\Gustavo\Integrador\integration-service\docker-compose.yml`
  - `C:\Gustavo\Integrador\mongodb\docker-compose.yml`
- Observacion directa de logs y runtime local del 2026-06-30