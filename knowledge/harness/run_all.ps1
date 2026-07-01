param(
  [switch]$DocsOnly
)

$scriptDir = $PSScriptRoot
if ($DocsOnly) {
  & (Join-Path $scriptDir 'docs_only_check.ps1')
  exit $LASTEXITCODE
}

Write-Host 'Full harness is defined but not executed automatically during bootstrap.'
Write-Host 'Detected candidate commands: mvn clean package -DskipTests ; docker compose up -d --build'
& (Join-Path $scriptDir 'docs_only_check.ps1')
exit $LASTEXITCODE

