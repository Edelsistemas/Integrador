#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/../.." && pwd)"
FAIL=0
check_path() {
  local path="$1"
  if [ -e "$ROOT/$path" ]; then
    echo "[PASS][CRITICAL] $path - OK"
  else
    echo "[FAIL][CRITICAL] $path - Missing"
    FAIL=1
  fi
}
check_path AGENTS.md
check_path knowledge
check_path knowledge/workflows/workflow-state.md
check_path knowledge/workflows/gates.md
check_path knowledge/workflows/commit-policy.md
check_path knowledge/workflows/validation-policy.md
check_path knowledge/agents/README.md
check_path knowledge/specs/README.md
if [ -d "$ROOT/Knowledge" ] && [ -d "$ROOT/knowledge" ]; then
  echo "[FAIL][CRITICAL] duplicate Knowledge/knowledge - Both exist"
  FAIL=1
else
  echo "[PASS][CRITICAL] duplicate Knowledge/knowledge - OK"
fi
if [ "$FAIL" -eq 0 ]; then
  echo 'DocsOnly Passed'
  exit 0
fi
echo 'DocsOnly Failed'
exit 1

