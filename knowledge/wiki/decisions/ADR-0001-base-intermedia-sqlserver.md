# ADR-0001 - Base intermedia SQL Server como contrato entre Teamcenter y SAP

## Estado
Approved histórico / documentado a posteriori

## Contexto

Teamcenter no escribe directamente en SAP. Descar creó una base SQL Server intermedia donde Teamcenter inserta registros por tipo de ítem ante workflows aprobados.

## Decisión

Mantener SQL Server `DB_Edelflex_SAP` como contrato operativo entre Teamcenter y el Integrador.

## Consecuencias positivas

- Desacopla Teamcenter de SAP.
- Permite reprocesamiento y auditoría.
- Permite que el Integrador trabaje por polling.

## Consecuencias negativas

- Requiere disciplina de estructura y calidad de datos.
- Si Teamcenter inserta datos incompletos, el Integrador puede fallar o producir resultados no esperados.
- Falta un campo explícito de fecha/hora de inserción según recomendación histórica.

## Evidencia / fuente

Correos, código y confirmación Edelflex.
