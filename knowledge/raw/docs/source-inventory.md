# Inventario de fuentes documentales

## Planillas recibidas

| Fuente | Tipo | Uso | Estado de confianza |
|---|---|---|---|
| `Mapeo de datos interfaz Articulos Teamcenter-SAP_rev3.xlsx` | Excel | Mapeo funcional acordado Teamcenter/SAP y grupos de artículos | Confirmado como fuente funcional, no necesariamente última implementación |
| `plantilla_migracion_tc_revision_v7_3.xlsm` | Excel macro | Exportación/relevamiento de ítems Teamcenter, estados y familias | Confirmado como evidencia de datos Teamcenter 2026 |
| `Mapeo de datos interfaz Articulos Teamcenter-SAP_rev6.xlsx` | Excel adjunto a correo | Revisión posterior del mapeo con ejemplos de ítems por grupo | Confirmado como evidencia histórica adjunta |
| Código fuente `ede-integration-service` | Java/Spring Boot | Última verdad técnica del comportamiento ejecutado | Máxima confianza técnica |

## Observaciones relevantes

- La planilla de mapeo rev3 usa códigos de grupos SAP `100` a `130` como referencia funcional.
- El código usa valores de grupos parametrizados por ambiente. En producción se observaron valores como `304` a `335`; en local/test valores como `133` a `164`.
- Esto es consistente con el correo donde se indicó que los ID de grupos SAP se numeran automáticamente y varían por base de datos.
