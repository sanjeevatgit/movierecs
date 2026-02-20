#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"
PID_FILE=".moviegeek_server.pid"

if [[ -f "$PID_FILE" ]]; then
  pid="$(cat "$PID_FILE" || true)"
  if [[ -n "${pid}" ]] && kill -0 "$pid" 2>/dev/null; then
    kill "$pid"
    rm -f "$PID_FILE"
    echo "Stopped MovieGeek server (PID $pid)"
    exit 0
  fi
  rm -f "$PID_FILE"
fi

if command -v pgrep >/dev/null 2>&1; then
  pids="$(pgrep -f 'manage.py runserver 127.0.0.1:8000 --noreload' || true)"
  if [[ -n "${pids}" ]]; then
    echo "$pids" | xargs kill
    echo "Stopped MovieGeek server process(es): $pids"
    exit 0
  fi
fi

echo "No running MovieGeek local server found."
