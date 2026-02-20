#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$ROOT_DIR"
PID_FILE=".moviegeek_server.pid"
LOG_DIR="logs"
LOG_FILE="$LOG_DIR/server.log"
server_pid=""

if [[ ! -f ".venv/bin/activate" ]]; then
  echo "Missing virtualenv at .venv. Create it first: python3 -m venv .venv"
  exit 1
fi

if [[ -f "$PID_FILE" ]]; then
  old_pid="$(cat "$PID_FILE" || true)"
  if [[ -n "${old_pid}" ]] && kill -0 "$old_pid" 2>/dev/null; then
    echo "Server already running with PID $old_pid"
    exit 0
  fi
  rm -f "$PID_FILE"
fi

cleanup() {
  if [[ -n "$server_pid" && -f "$PID_FILE" ]]; then
    current_pid="$(cat "$PID_FILE" || true)"
    if [[ "$current_pid" == "$server_pid" ]]; then
      rm -f "$PID_FILE"
    fi
  fi
}

trap cleanup EXIT

mkdir -p "$LOG_DIR"

source .venv/bin/activate
python manage.py runserver 127.0.0.1:8000 --noreload >>"$LOG_FILE" 2>&1 &
server_pid=$!
echo "$server_pid" > "$PID_FILE"
echo "MovieGeek server started at http://127.0.0.1:8000 (PID $server_pid)"
echo "Logs: $LOG_FILE"
wait "$server_pid"
