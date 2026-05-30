#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

LIB="lib/gson-2.10.1.jar"
OUT="out"

mkdir -p "$OUT"
find src -name '*.java' > .sources.tmp
javac -cp "$LIB" -d "$OUT" @.sources.tmp
rm -f .sources.tmp

echo "Build OK -> $OUT"
