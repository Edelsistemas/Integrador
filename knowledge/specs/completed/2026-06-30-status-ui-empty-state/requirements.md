# Requirements - Draft

## REQ-001
Cuando no exista ningun `BatchProcess` para `SYNC_ITEMS`, la pantalla `/` no debe responder `500`.

## REQ-002
Cuando no exista ningun `BatchProcess`, la pantalla debe mostrar un estado legible equivalente a "sin ejecuciones" o similar.

## REQ-003
Cuando no existan resultados de items, el modal de detalle no debe mostrarse como disponible.

## REQ-004
El boton de refresco debe apuntar a un endpoint realmente implementado.

## NFR-001
La solucion no debe depender de insertar registros dummy en MongoDB para funcionar correctamente.

## NFR-002
La solucion debe seguir siendo compatible con ejecuciones reales ya existentes en Mongo.