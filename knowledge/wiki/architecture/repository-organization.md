# Repository organization

## Root monorepo

Repositorio raiz actual: `C:\Gustavo\Integrador`

## Estructura observada

- `integration-service/`
  - `docker-compose.yml`
  - `build.sh`
  - `sources/ede-integration-service/` (repo Java importado al monorepo)
- `mongodb/`
  - `docker-compose.yml`
  - `data/` como estado local no versionado
- `knowledge/`
  - wiki
  - specs
  - agents
  - workflows
  - prompts
  - harness
  - outputs
  - raw

## Convenciones adoptadas

- `knowledge/` en minuscula es la fuente documental principal.
- No se recreo la wiki desde cero; se completo sobre la estructura existente.
- La historia del repo Java se preservo dentro del monorepo raiz.

