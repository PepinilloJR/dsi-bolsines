# Ubicación del proyecto
PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"

cd "$PROJECT_DIR"

echo "=============================="
echo " INICIANDO DSI-BOLSINES"
echo "=============================="

# Levantar los servicios
docker compose up -d --no-build

echo "=============================="
echo " SERVICIOS INICIADOS"
echo "=============================="

# Mostrar estado
docker compose ps
