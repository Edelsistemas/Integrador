# Security Review - As-is

## Resultado
Requiere cambios para nueva revisión.

## Hallazgos

- Credenciales y API keys aparecen en YAML.
- Se deshabilita validación estricta de certificados al construir cliente SAP.
- Existe envío externo de trazas/logs a BitOne.
- Basic Auth de endpoint `/` está hardcodeado en código según análisis previo.

## Controles requeridos

- Externalizar secretos.
- Deshabilitar BitOne.
- Revisar TLS/certificados internos.
- Definir política de logs sin datos sensibles.
