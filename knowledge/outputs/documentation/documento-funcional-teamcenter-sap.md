# Documento funcional - Teamcenter a SAP B1

## Objetivo funcional

Sincronizar artículos aprobados/liberados en Teamcenter hacia SAP Business One.

## Flujo funcional

1. Un usuario de Edelflex crea o modifica un ítem en Teamcenter.
2. Se ejecuta workflow de autorización.
3. Si el workflow queda aprobado, Teamcenter expone estado `60`.
4. Teamcenter inserta un registro en la tabla intermedia SQL correspondiente al tipo de ítem.
5. El Integrador detecta el registro pendiente.
6. El Integrador crea o modifica el artículo en SAP.
7. El Integrador marca el registro como procesado.

## Estados funcionales

| Estado | Significado | Acción esperada |
|---|---|---|
| 60 | Aprobado/liberado | Migrar/actualizar en SAP |
| 90 | Obsoleto/baja | Inactivar en SAP según cambio funcional |

## CREATE vs UPDATE

- Si `ItemCode` no existe en SAP: crear artículo.
- Si `ItemCode` existe en SAP: actualizar artículo.

## Campos críticos de inactivación

Según correos, los campos críticos definidos fueron:

- Descripción.
- Grupo unidad de medida.
- Importado.
- Fabricado.

Según código analizado, la comparación efectiva es:

- `ItemName`.
- `ItemsGroupCode`.
- `Properties1`.
- `Properties2`.
- Estado `90`.

Esta diferencia entre `Grupo unidad de medida` e `ItemsGroupCode` debe revisarse.

## Auditoría

MongoDB registra información histórica/técnica del procesamiento. No es la fuente funcional del dato.
