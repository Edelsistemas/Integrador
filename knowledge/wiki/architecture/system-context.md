# System context

## Sistemas externos

| Sistema | Rol | Responsable |
|---|---|---|
| Teamcenter PLM | Origen de artículos, workflows y estados | Descar / Edelflex |
| SQL Server `DB_Edelflex_SAP` | Base intermedia escrita por Teamcenter y leída por Integrador | Descar / Edelflex IT |
| SAP Business One Service Layer | API destino para maestro de artículos | Techtherapy / Edelflex |
| MongoDB | Auditoría técnica del Integrador | Integrador / Edelflex IT |
| BitOne | Soporte/trazas legacy externo | Bit-One / 4Points, a retirar |

## Límites del sistema

El Integrador no debería decidir si un dato Teamcenter es correcto desde negocio; debe procesar o reportar error. La calidad de datos que se inserta en la base intermedia corresponde al origen Teamcenter/Descar, salvo validaciones técnicas propias del Integrador.
