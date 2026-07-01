# Dependency map

## Dependencias Maven principales

- Spring Boot Web
- Spring Boot Security
- Spring Boot Data MongoDB
- Spring Boot Thymeleaf
- Spring Batch
- SQL Server JDBC driver
- SAP HANA JDBC `ngdbc`
- Apache HttpClient 5
- Lombok

## Servicios externos

| Servicio | Uso | Riesgo |
|---|---|---|
| SQL Server | Base intermedia | Conectividad/permisos/datos incompletos |
| MongoDB | Auditoría | Pérdida de trazabilidad si falla |
| SAP Service Layer | Destino funcional | Errores de autenticación, payload o disponibilidad |
| BitOne | Trazas legacy | Debe retirarse |

## Secretos

La wiki no registra valores reales. El código analizado contiene credenciales/API keys en YAML; deben ser externalizadas.
