# Registro de riesgos

| ID | Riesgo | Impacto | Probabilidad | Mitigación | Estado | Fuente |
|---|---|---|---|---|---|---|
| RSK-001 | Criterio de pendiente ambiguo entre `Status` y `Fecha_Proceso` | Reprocesos inesperados o registros sin procesar | Media | Verificar configuración productiva exacta y SQL real | Abierto | Código + prueba Edelflex |
| RSK-002 | BitOne sigue configurado en producción | Salida de trazas a tercero / dependencia externa | Alta | Deshabilitar perfiles y appender; retirar config | Abierto | Código + Edelflex |
| RSK-003 | Secretos hardcodeados en YAML | Exposición de credenciales | Alta | Externalizar a variables/secret manager; rotar si fue expuesto | Abierto | Código |
| RSK-004 | Sin tests de reglas críticas | Cambios futuros pueden romper CREATE/UPDATE/inactivación | Alta | Crear harness con tests unitarios/integración simulada | Abierto | Código |
| RSK-005 | Diferencias entre datos SAP existentes y Teamcenter disparan inactivaciones | Falsos positivos funcionales en pruebas | Media | Casos de prueba completos en misma base; comparar origen/destino | Abierto | PDF mayo 2025 |
| RSK-006 | Grupos SAP difieren por base | Mapeo incorrecto por ambiente | Alta | Parametrizar y documentar IDs por ambiente | Abierto | Correo + config |
| RSK-007 | Teamcenter puede no insertar `fecha_hora_registro` | Dificulta auditoría de no migrados/intermitencias | Media | Solicitar campo de trazabilidad en base intermedia | Abierto | Correo mayo 2025 |
| RSK-008 | Artículos nuevos se crean inactivos por código | Puede ser esperado, pero debe quedar validado como regla actual | Media | Confirmar requerimiento vigente y testear | Abierto | Código + correos |
