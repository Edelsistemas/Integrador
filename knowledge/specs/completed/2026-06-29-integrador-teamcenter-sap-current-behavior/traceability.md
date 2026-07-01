# Traceability

| Requisito | Evidencia código | Test / validación | Estado |
|---|---|---|---|
| REQ-001 | `application.yml` query + `SQLServerService.getProducts` | Consulta SQL testing | Pending |
| REQ-002 | `SyncBaseProductProcessor` + `SapItemService.create` | Caso ItemCode inexistente | Pending |
| REQ-003 | `SyncBaseProductProcessor` + `SapItemService.update` | Caso ItemCode existente | Pending |
| REQ-004 | `Product.evaluateInactivo` | Casos campos críticos / estado 90 | Pending |
| REQ-005 | `SQLServerService.updateProducts` | Verificar campos SQL | Pending |
| REQ-006 | `SyncBaseProductWriter` | Verificar Mongo | Pending |
