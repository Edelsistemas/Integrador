# Acceptance Criteria - As-is

## AC-001
Dado un registro pendiente con `ItemCode` inexistente en SAP, cuando corre el batch, entonces se llama `POST /Items`.

## AC-002
Dado un registro pendiente con `ItemCode` existente en SAP, cuando corre el batch, entonces se llama `PATCH /Items('<code>')`.

## AC-003
Dado un artículo existente donde cambia descripción, grupo, importado, fabricado o estado `90`, cuando se procesa, entonces el payload SAP incluye `Valid=tNO` y `Frozen=tYES`.

## AC-004
Dado un resultado SAP exitoso, cuando termina el writer, entonces SQL Server queda con `Status=OK`, `Fecha_Proceso` actualizada, `Mensaje_Proceso` y `Id_Proceso`.

## AC-005
Dado un error SAP, cuando termina el writer, entonces SQL Server queda con `Status=ERROR` y mensaje de error.
