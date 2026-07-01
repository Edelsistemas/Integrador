# API Contract - SAP Service Layer

## POST /b1s/v1/Login
Obtiene sesión SAP.

## GET /b1s/v1/Items/$count?$filter=ItemCode eq '<code>'
Determina existencia del artículo.

## GET /b1s/v1/Items?$select=ItemName,ItemsGroupCode,Properties1,Properties2&$filter=ItemCode eq '<code>'
Obtiene datos actuales usados para evaluar inactivación.

## POST /b1s/v1/Items
Crea artículo.

## PATCH /b1s/v1/Items('<code>')
Actualiza artículo.
