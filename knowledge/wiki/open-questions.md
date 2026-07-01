# Preguntas abiertas

## Confirmadas y cerradas

- `main-GS` existe y es la rama de trabajo activa local.
- `RECOVERY-ORIGINAL` y `DOCKER-STABLE` existen y apuntan a commits distintos.
- El stack local actual puede levantar en Docker Desktop con Mongo local y Spring Boot local.

## Abiertas

1. La version productiva exacta filtra pendientes por `Status`, por `Fecha_Proceso` o por ambos?
2. Existe trigger, vista o proceso SQL que conecte `Fecha_Proceso` con `Status`?
3. Crear articulos nuevos inactivos sigue siendo regla funcional vigente?
4. El campo critico historico `Grupo unidad de medida` corresponde en codigo a `ItemsGroupCode` o hay una brecha funcional?
5. Se debe agregar `fecha_hora_registro` en tablas intermedias?
6. MongoDB se conserva como auditoria interna en la nueva revision propia?
7. Hay informacion historica de BitOne que Edelflex quiera conservar antes de retirarlo?
8. Que version exacta esta desplegada en produccion respecto de `master`, `main-GS` y `DOCKER-STABLE`?
9. La pantalla `/` debe mostrar estado vacio amigable cuando no existe ningun `BatchProcess`, o se prefiere sembrar un registro inicial?
10. El flujo oficial de desarrollo local debe exigir siempre `mvn clean package` previo al compose o se permitira configuracion externa montada para acelerar debug?
11. Que set de casos de prueba debe considerarse aceptacion minima para nueva revision propia?

## Pendientes de verificacion tecnica

- Confirmar si el compose local final debe usar Mongo en `10.15.0.3:27017` como estandar de equipo o si eso es solo una adaptacion temporal de esta maquina.
- Confirmar si el `500` de `/` en base limpia ya existe en produccion o solo emerge en instalaciones nuevas sin datos previos.
- Confirmar si el endpoint de refresco pretendido era `/` o `/process/status`, dado que el HTML hoy apunta a una ruta no implementada.
