#!/bin/bash

set -e

SERVICES=(
  "frontend"
  "backend"
  "agps"
)

# Directorio donde está ubicado el script
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"

echo "=== Checking Docker login ==="

if docker info 2>/dev/null | grep -q "Username:"; then
    echo "Estás logueado en Docker Hub"
    LOGGED_IN=true
else
    echo "No estás logueado en Docker Hub"
    LOGGED_IN=false
fi

for service in "${SERVICES[@]}"; do

    echo "=============================="
    echo "BUILDING: $service"
    echo "=============================="

    cd "$PROJECT_DIR/$service"

    # Compilar backend con Maven
    if [ "$service" = "backend" ]; then
        mvn clean package -DskipTests
    fi

    # Construir imagen
    IMAGE="pepinillojr/tpi-bolsines:$service"

    docker build -t "$IMAGE" .

    # Publicar imagen
    if [ "$LOGGED_IN" = true ]; then
        echo "Haciendo push de $IMAGE"
        docker push "$IMAGE"
    else
        echo "Saltando push"
    fi

done

echo "=============================="
echo "BUILD COMPLETED"
echo "=============================="

echo "=============================="
echo "CLEANING THE FUNNY"
echo "=============================="

docker image prune -f
