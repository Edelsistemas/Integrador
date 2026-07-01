# Harness Runbook

## Precondiciones

- Java 21 configurado.
- Maven operativo.
- Docker Desktop operativo.
- Repo clonado.
- Perfil local seguro.

## 1. Build

Carpeta:

```text
C:\Gustavo\integration-service\sources\ede-integration-service
```

Comando:

```powershell
mvn clean package
```

Resultado esperado: `BUILD SUCCESS`.

Si falla: revisar Java, Maven, certificados, dependencias.

## 2. Docker local

Carpeta:

```text
C:\Gustavo\integration-service
```

Comando:

```powershell
docker compose up --build
```

Resultado esperado: app inicia con perfil seguro y no dispara batches si no se activaron perfiles.

## 3. Git estado

```powershell
git branch -a
git tag
git show-ref --tags
git log --oneline --graph --decorate --all
```

Si el log queda en paginador, presionar `q`.
