# ede-integration-service

Servicio Spring Boot para integrar datos de items/productos de Teamcenter/SQL Server con SAP Business One Service Layer.

El proceso principal lee registros pendientes desde SQL Server, arma requests de items para SAP, ejecuta altas o actualizaciones, guarda historial en MongoDB y expone una pantalla simple de estado.

## Estado del repositorio

La rama activa para desarrollo y debug local es:

```bash
main-GS
```

La rama `master` se conserva como referencia de la version original recuperada desde ZIP/backup. No debe usarse como base de desarrollo salvo que se quiera volver explicitamente al estado original.

Estado esperado de ramas:

```txt
master         -> 94c01e9 Finish Hotfix-Fix-Inactive-Request
origin/master  -> 94c01e9 Finish Hotfix-Fix-Inactive-Request

main-GS        -> 1b2d691 Desacople de batch launchers y configuracion local Docker
origin/main-GS -> 1b2d691 Desacople de batch launchers y configuracion local Docker
```

## Tags de referencia

Hay dos tags importantes:

```txt
RECOVERY-ORIGINAL -> 94c01e9
DOCKER-STABLE     -> 1b2d691
```

`RECOVERY-ORIGINAL` marca el commit original recuperado.

`DOCKER-STABLE` marca el commit donde Docker/Spring Boot levanta estable con perfil local.

Para evitar errores, cuando un tag deba apuntar a un punto historico especifico, crearlo siempre indicando el hash:

```bash
git tag NOMBRE_DEL_TAG HASH_DEL_COMMIT
```

Ejemplo:

```bash
git tag RECOVERY-ORIGINAL 94c01e9
```

No usar `git tag RECOVERY-ORIGINAL` sin hash si el objetivo es marcar un commit historico, porque Git lo crea sobre el `HEAD` actual.

## Verificacion Git

Comandos utiles para verificar el estado:

```bash
git branch -a
git tag
git show-ref --tags
git log --oneline --graph --decorate --all
```

El log esperado debe mostrar algo equivalente a:

```txt
1b2d691 (HEAD -> main-GS, origin/main-GS, tag: DOCKER-STABLE) Desacople de batch launchers y configuracion local Docker
94c01e9 (master, origin/master, tag: RECOVERY-ORIGINAL) Finish Hotfix-Fix-Inactive-Request
```

## Perfiles de ejecucion

La aplicacion usa perfiles Spring para activar los procesos batch:

```txt
sync-items-batch        Sincroniza items SQL Server -> SAP
send-process-data-batch Envia trazas de procesos a BitOne
clean-up-process-batch  Limpia historial viejo de items en MongoDB
local                   Configuracion local
dev                     Configuracion de desarrollo
prod                    Configuracion productiva
```

El batch automatico de Spring esta deshabilitado por defecto en `application.yml`:

```yaml
spring:
  batch:
    job:
      enabled: false
```

Los launchers propios disparan los jobs con `@Scheduled` cuando los perfiles correspondientes estan activos.

## Configuracion sensible

Los archivos `application-*.yml` contienen configuracion de infraestructura. No agregar nuevas credenciales reales al repositorio.

Para evolucionar el proyecto, se recomienda mover credenciales y API keys a variables de entorno, secretos del entorno de despliegue o un vault.

## Build y Docker

El proyecto usa Java 21 y Maven.

Build esperado:

```bash
mvn clean package
```

El Dockerfile espera el artefacto:

```txt
target/edelflex-integration-service-1.0.0.jar
```

La imagen ejecuta la aplicacion con timezone:

```txt
America/Argentina/Buenos_Aires
```

## UI de estado

La pantalla principal esta publicada en `/` y muestra el ultimo proceso de items, estado, fechas y detalle de requests/responses.

Esta protegida con Basic Auth.

## Notas operativas

Antes de commitear, revisar:

```bash
git status -sb
```

No commitear archivos temporales de logs, capturas o salidas de comandos salvo que sean documentacion intencional.
