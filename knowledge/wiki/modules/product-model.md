# Módulo: Product

## Propósito

Representar un artículo proveniente de Teamcenter/SQL Server y construir el payload SAP.

## Responsabilidades

- Guardar datos origen.
- Evaluar si corresponde inactivar.
- Mapear a campos SAP estándar y UDF.
- Convertir importado/fabricado a `Properties1`/`Properties2` con valores `tYES`/`tNO`.
- Eliminar valores vacíos del payload.

## Reglas de inactivación detectadas

En actualización, marca `inactivo=true` si:

- Cambia `ItemName`.
- Cambia `ItemsGroupCode`.
- Cambia `Properties1` / importado.
- Cambia `Properties2` / fabricado.
- Estado Teamcenter es `90`.

En creación, el processor establece `inactivo=true` antes de crear el request.

## Estado
Confirmado por código.
