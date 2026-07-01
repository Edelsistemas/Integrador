#!/usr/bin/env bash
set -euo pipefail
if [ "${1:-}" = "--docs-only" ]; then
  bash "$(dirname "$0")/docs_only_check.sh"
  exit $?
fi
echo 'Full harness is not executed automatically during bootstrap.'
bash "$(dirname "$0")/docs_only_check.sh"

