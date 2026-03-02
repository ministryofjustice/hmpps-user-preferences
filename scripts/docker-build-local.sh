#!/bin/bash
# Build and run the Docker image locally.
# Usage: ./scripts/docker-build-local.sh

set -euo pipefail

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_ROOT"

echo "==> Building application JAR..."
./gradlew assemble

# Find the built JAR and extract the version
JAR_PATH=$(ls build/libs/hmpps-user-preferences-*.jar 2>/dev/null | head -1)
if [ -z "$JAR_PATH" ]; then
  echo "ERROR: No JAR found in build/libs/" >&2
  exit 1
fi

JAR_NAME=$(basename "$JAR_PATH")
BUILD_NUMBER="${JAR_NAME#hmpps-user-preferences-}"
BUILD_NUMBER="${BUILD_NUMBER%.jar}"

echo "==> Found JAR: $JAR_NAME (BUILD_NUMBER=$BUILD_NUMBER)"

# Copy artifacts to project root (matching CI behaviour)
cp "$JAR_PATH" .
cp build/libs/applicationinsights-agent-*.jar .

echo "==> Building Docker image..."
docker build --build-arg BUILD_NUMBER="$BUILD_NUMBER" -t hmpps-user-preferences:local .

# Clean up copied files
rm -f "hmpps-user-preferences-${BUILD_NUMBER}.jar" applicationinsights-agent-*.jar

echo "==> Done! Run with:"
echo "    docker run -p 8080:8080 hmpps-user-preferences:local"

