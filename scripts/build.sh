#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
if ! command -v java >/dev/null 2>&1; then
  echo "JDK 21 is required. Install Temurin 21 and set JAVA_HOME." >&2
  exit 1
fi
chmod +x ./gradlew
./gradlew build --stacktrace "$@"
echo "JAR: $(ls -1 build/libs/*.jar 2>/dev/null || true)"
