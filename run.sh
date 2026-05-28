#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")"

LIB="lib/gson-2.10.1.jar"
OUT="out"

./build.sh
java -cp "$OUT:$LIB" Main
