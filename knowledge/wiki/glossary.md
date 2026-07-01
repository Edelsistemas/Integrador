# Glosario

## Integrador
Aplicación Java/Spring Boot que sincroniza artículos entre Teamcenter y SAP B1.

## Teamcenter
PLM origen de artículos, revisiones, workflows y estados.

## SAP B1 Service Layer
API REST/OData de SAP Business One usada para crear/modificar artículos.

## Base intermedia
Base SQL Server escrita por Teamcenter y consumida por el Integrador.

## Estado 60
Aprobado/liberado/autorizado para SAP.

## Estado 90
Obsoleto/baja/inactivo en Teamcenter.

## Campo crítico
Dato cuya modificación puede inactivar el artículo en SAP. Código actual compara descripción, grupo de artículo, importado y fabricado. Correos mencionan grupo unidad de medida; en código se observa comparación de `ItemsGroupCode`, no `InventoryUOM`.

## `Fecha_Proceso`
Campo SQL actualizado por el Integrador al procesar. Edelflex confirmó que al vaciarlo manualmente se observó reproceso; requiere validar contra query productiva.

## `Status`
Campo SQL que el código usa para seleccionar pendientes (`NULL` o `ERROR`) y actualizar resultado (`OK` o `ERROR`).

## BitOne
Empresa/herramienta asociada al desarrollo/soporte legacy. No debe continuar en nueva revisión propia.
