# Safety constraints

## Ambientes y datos

- `application-prod.yml` contiene parametros sensibles y no debe cambiarse sin autorizacion explicita.
- Existen secretos versionados en YAML; no replicarlos en markdowns, prompts, reportes ni handoffs.
- `mongodb/data/` es estado local y no debe versionarse ni manipularse destructivamente sin pedido explicito.

## Operaciones prohibidas por defecto

- push sin autorizacion
- commit sin `AUTHORIZE_COMMIT`
- cambios de codigo productivo durante bootstrap
- borrado de archivos sin instruccion humana
- ejecucion de batches productivos
- tests o builds costosos no autorizados si pueden alterar ambientes

## Archivos protegidos

- `integration-service/sources/ede-integration-service/src/main/resources/application-prod.yml`
- credenciales reales o endpoints sensibles detectados en YAML
- cualquier archivo fuera del scope de la spec o gate activo

## Restricciones pendientes de definir por humano

- politica final de secretos por ambiente
- politica de acceso a ambientes productivos
- politica de retencion de auditoria Mongo

