# Integration map

## SAP Service Layer usado por código

| Operación | Endpoint |
|---|---|
| Login | `POST /b1s/v1/Login` |
| Existe artículo | `GET /b1s/v1/Items/$count?$filter=ItemCode eq '<code>'` |
| Datos actuales | `GET /b1s/v1/Items?$select=ItemName,ItemsGroupCode,Properties1,Properties2&$filter=ItemCode eq '<code>'` |
| Crear artículo | `POST /b1s/v1/Items` |
| Actualizar artículo | `PATCH /b1s/v1/Items('<code>')` |

## Colecciones MongoDB

| Colección | Uso |
|---|---|
| `items-process-history` | Histórico por ítem procesado |
| `batch-process` | Estado/traza de procesos batch, enviada por legacy a BitOne |

## Dependencias de red

- SQL Server interno.
- MongoDB interno.
- SAP Service Layer interno.
- BitOne externo en versión legacy, a retirar.
