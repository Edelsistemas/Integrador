# Extractos de evidencia desde correos

## Ambientes y base propia del integrador

Correo `RE: Primera devolución alcances interfaz Teamcenter-SAPB1`, 2024-06-06:

- Se pide disponer ambientes Desarrollo/Testing/QA y Producción.
- 4Points confirma que esa es su metodología.
- La base de datos de la interfaz se describe como propia del integrador, relacionada a configuraciones, mapeo y trazabilidad.

Estado: Confirmado por correo.

## Acceso a esquema SQL intermedio

Correo `RE: Interno Edelflex / Acceso a base de datos.`, 2024-09-27:

- Juan Manuel Vazquez informa que intenta conectar al esquema `DB_Edelflex_SAP`.
- Se mencionan permisos para usuario de 4Points.
- Se menciona que Descar debía completar tablas para la totalidad de ítems.
- Se menciona el issue de mapeo entre grupo de item Teamcenter y grupo de artículo SAP, porque SAP B1 numera grupos automáticamente y el rango varía por base.

Estado: Confirmado por correo.

## Estados 60 y 90

Correo `RE: [EDELFLEX] Revisión Interfaz - Minuta 15-Oct`:

- Estado 60: aprobación para producción.
- Estado 90: obsoleto.
- Se indica que los workflows asegurarían que a la base intermedia solo lleguen estados 60 y 90.

Estado: Confirmado por correo y por Edelflex.

## Reglas de inactivación por campos críticos

Correo `RE: [EDELFLEX] Definiciones de Cambio`, 2025-02-13:

- Caso 2: ante modificación de artículos existentes, si se modifica alguno de los campos críticos, el artículo debe inactivarse en SAP.
- Campos críticos mencionados: descripción, grupo unidad de medida, importado, fabricado.
- Caso 3: ante baja estado 90, el artículo debe pasar a inactivo en SAP.

Estado: Confirmado por correo, requiere contraste con código.

## Análisis de pruebas de mayo

Correo `RE: [EDELFLEX] Resultados de Prueba al 7.May` y PDF `EDELFLEX - Revision del Inactivar por campos críticos 20250508.pdf`:

- Se analizó un caso donde se esperaba actualizar sin inactivar, pero el Integrador inactivó porque la descripción en SAP difería de la descripción proveniente de Teamcenter.
- Se explicó que comparar contra una base SAP distinta o con datos preexistentes distintos puede producir inactivaciones esperadas desde la lógica actual.
- Se recomendó agregar un campo `fecha_hora_registro` en tablas intermedias para poder seguir cuándo se insertó cada registro.

Estado: Confirmado por correo/PDF.

## Productivo

Correo `RE: [EDELFLEX] Inicio Productivo`, 2025-07-15:

- Evidencia inicio productivo / salida a producción del proceso.
- Requiere análisis más profundo si se necesita una cronología de salida a PROD.

Estado: Confirmado por correo.
