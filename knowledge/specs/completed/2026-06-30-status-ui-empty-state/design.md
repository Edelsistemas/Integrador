# Design - Draft

## Componentes afectados

- `InfoController`
- `status.html`

## Enfoque propuesto

1. Mantener `batchItems` nullable en controller.
2. Hacer null-safe la plantilla Thymeleaf para fechas, estado y disponibilidad del modal.
3. Mostrar placeholders cuando no haya ejecuciones previas.
4. Corregir el `th:action` del form de refresco si el endpoint final confirmado sigue siendo `/`.

## Riesgos

- Si se corrige solo con datos dummy, el bug de base limpia seguira oculto.
- Si se cambia el endpoint de refresco sin revisar navegacion completa, puede quedar documentacion desalineada.

## Verificacion esperada

- `GET /` autenticado sobre Mongo vacio responde `200`.
- La pagina muestra placeholders en vez de error.
- `GET /` autenticado sobre Mongo con datos reales sigue mostrando la informacion historica.