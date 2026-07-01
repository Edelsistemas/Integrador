# Módulo: BitOne legacy

## Propósito histórico

BitOne aparece en dos lugares:

- `SendProcessDataTasklet`: envía `BatchProcess` a un endpoint externo.
- `BitOneAppender`: appender logback para enviar logs externos.

## Decisión Edelflex

No debe continuar como dependencia en la nueva revisión propia.

## Riesgos

- Exfiltración de trazas o información técnica a tercero.
- Dependencia externa no necesaria para el objetivo funcional Teamcenter -> SAP.
- Credenciales/API key en configuración.

## Estado
Confirmado por código y por Edelflex.
