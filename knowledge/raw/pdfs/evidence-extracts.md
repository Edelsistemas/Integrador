# Extractos desde PDF

## PDF: EDELFLEX - Revision del Inactivar por campos críticos 20250508.pdf

Ubicación original: adjunto del correo `RE: [EDELFLEX] Resultados de Prueba al 7.May`.

### Hallazgo principal

El caso `VBF000100` fue revisado porque se esperaba una actualización sin inactivar, pero SAP terminó requiriendo/recibiendo inactivación. La revisión concluye que la descripción en SAP no coincidía con la descripción de Teamcenter; por lo tanto, para la lógica del Integrador se trataba de una modificación de campo crítico.

### Implicancia

La lógica de inactivación no compara solamente revisión contra revisión dentro de Teamcenter. En la práctica compara datos entrantes desde Teamcenter contra datos existentes en SAP. Si SAP ya tiene datos distintos, un cambio puede disparar inactivación aunque el usuario no haya modificado ese campo en Teamcenter durante esa prueba.

### Recomendación histórica detectada

Para pruebas, se recomendó ejecutar casos completos sobre la misma base SAP, generando revisión anterior y revisión posterior en la misma base.

Estado: Confirmado por PDF extraído.
