# Requirements - As-is

## REQ-001
Cuando exista un registro pendiente en una tabla intermedia, el Integrador debe procesarlo.

## REQ-002
Cuando el `ItemCode` no exista en SAP, el Integrador debe crear el artículo.

## REQ-003
Cuando el `ItemCode` exista en SAP, el Integrador debe actualizar el artículo.

## REQ-004
Cuando cambie un campo crítico o el estado sea `90`, el Integrador debe enviar el artículo como inactivo en SAP.

## REQ-005
Cuando termine el intento de procesamiento, el Integrador debe actualizar la tabla intermedia con resultado, fecha, mensaje e ID de proceso.

## REQ-006
El Integrador debe registrar auditoría técnica del procesamiento en MongoDB.

## NFR-001
Los cambios futuros no deben depender de BitOne.

## NFR-002
Los secretos no deben quedar hardcodeados en repositorio.
