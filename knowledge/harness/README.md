# Harness

## Proposito

Validar documentacion, workflow y, cuando haya autorizacion, chequeos tecnicos del repo.

## Modos

### DocsOnly

Chequea estructura documental, workflows, agentes, prompts, specs y politicas minimas.

### Full

Reservado para ejecucion autorizada. Candidatos detectados:
- Maven package
- docker compose local
- chequeos documentales y de secretos

## Script primario

- Windows / VSCode local: `knowledge/harness/run_all.ps1`
- Bash complementario: `knowledge/harness/run_all.sh`

