# Test Plan - As-is

## Validaciones mínimas

1. Build Maven.
2. Arranque local con perfil seguro `local` sin batches.
3. Test de SELECT pendiente contra base de testing.
4. Test CREATE con SAP testing.
5. Test UPDATE sin campos críticos.
6. Test UPDATE con campo crítico.
7. Test estado `90`.
8. Test error SAP y reproceso.
9. Test que BitOne no se invoque en revisión propia.

## Comandos sugeridos

```powershell
mvn clean package
```

```powershell
docker compose up --build
```

## Resultado esperado

Build exitoso y arranque sin ejecutar procesos productivos salvo que se activen perfiles explícitos de prueba.
