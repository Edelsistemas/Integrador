param()

$ErrorActionPreference = 'Stop'
$root = Split-Path -Parent (Split-Path -Parent $PSScriptRoot)
$checks = New-Object System.Collections.Generic.List[object]

function Add-Check {
  param(
    [string]$Name,
    [bool]$Passed,
    [bool]$Critical,
    [string]$Detail
  )

  $checks.Add([pscustomobject]@{
    Name = $Name
    Passed = $Passed
    Critical = $Critical
    Detail = $Detail
  }) | Out-Null
}

function Require-Path {
  param(
    [string]$Relative,
    [bool]$Critical = $true
  )

  $full = Join-Path $root $Relative
  $exists = Test-Path $full
  $detail = if ($exists) { 'OK' } else { 'Missing' }
  Add-Check -Name $Relative -Passed $exists -Critical $Critical -Detail $detail
}

function Require-NonEmptyMarkdown {
  param(
    [string]$Relative,
    [bool]$Critical = $true
  )

  $full = Join-Path $root $Relative
  if (-not (Test-Path $full)) {
    Add-Check -Name $Relative -Passed $false -Critical $Critical -Detail 'Missing'
    return
  }

  $text = (Get-Content -Raw $full).Trim()
  $passed = $text.Length -gt 0
  $detail = if ($passed) { 'OK' } else { 'Empty markdown' }
  Add-Check -Name $Relative -Passed $passed -Critical $Critical -Detail $detail
}

$requiredPaths = @(
  'AGENTS.md',
  'knowledge',
  'knowledge/workflows',
  'knowledge/agents',
  'knowledge/specs/draft',
  'knowledge/specs/approved',
  'knowledge/specs/completed',
  'knowledge/specs/archived',
  'knowledge/prompts/gates',
  'knowledge/prompts/maintenance'
)
foreach ($p in $requiredPaths) {
  Require-Path -Relative $p
}

$criticalMarkdowns = @(
  'knowledge/workflows/workflow-state.md',
  'knowledge/workflows/gates.md',
  'knowledge/workflows/commit-policy.md',
  'knowledge/workflows/validation-policy.md',
  'knowledge/workflows/safety-constraints.md',
  'knowledge/agents/README.md',
  'knowledge/specs/README.md',
  'knowledge/harness/README.md',
  'knowledge/wiki/project-overview.md',
  'knowledge/wiki/architecture/current-state.md'
)
foreach ($m in $criticalMarkdowns) {
  Require-NonEmptyMarkdown -Relative $m
}

$dirNames = @((Get-ChildItem -Directory -Force $root | Select-Object -ExpandProperty Name))
$hasKnowledgeLower = $dirNames -ccontains 'knowledge'
$hasKnowledgeUpper = $dirNames -ccontains 'Knowledge'
$dupKnowledge = $hasKnowledgeLower -and $hasKnowledgeUpper
$dupDetail = if ($dupKnowledge) { 'Both Knowledge and knowledge exist' } else { 'OK' }
Add-Check -Name 'duplicate Knowledge/knowledge' -Passed (-not $dupKnowledge) -Critical $true -Detail $dupDetail

$gatesFile = Join-Path $root 'knowledge/workflows/gates.md'
$gateNames = @(
  'HUMAN_GATE: APPROVE_SPEC',
  'HUMAN_GATE: REQUEST_SPEC_CHANGES',
  'HUMAN_GATE: START_IMPLEMENTATION',
  'HUMAN_GATE: REQUEST_VALIDATION',
  'HUMAN_GATE: ACCEPT_RESULT',
  'HUMAN_GATE: REJECT_RESULT',
  'HUMAN_GATE: COMPLETE_SPEC',
  'HUMAN_GATE: AUTHORIZE_COMMIT'
)
if (Test-Path $gatesFile) {
  $gateContent = Get-Content -Raw $gatesFile
  foreach ($gate in $gateNames) {
    $present = $gateContent -match [regex]::Escape($gate)
    $detail = if ($present) { 'OK' } else { 'Missing gate' }
    Add-Check -Name "gate $gate" -Passed $present -Critical $true -Detail $detail
  }
}

$failedCritical = @($checks | Where-Object { (-not $_.Passed) -and $_.Critical }).Count
$failedNonCritical = @($checks | Where-Object { (-not $_.Passed) -and (-not $_.Critical) }).Count
$passedCount = @($checks | Where-Object { $_.Passed }).Count

foreach ($c in $checks) {
  $status = if ($c.Passed) { 'PASS' } else { 'FAIL' }
  $level = if ($c.Critical) { 'CRITICAL' } else { 'WARN' }
  Write-Host "[$status][$level] $($c.Name) - $($c.Detail)"
}

Write-Host "DocsOnly Summary: Passed=$passedCount FailedCritical=$failedCritical FailedNonCritical=$failedNonCritical"
if ($failedCritical -eq 0) {
  Write-Host 'DocsOnly Passed'
  exit 0
}

Write-Host 'DocsOnly Failed'
exit 1


