#!/bin/bash
set -e
cd `dirname $0`/..
ROOT=`pwd`

cleanup() {
  echo -e "\rCleaning up..."
  pkill -P $$ || true
  docker-compose -f $ROOT/scripts/docker-compose.yaml down --volumes 2>&1 > /dev/null || true
  echo "Done."
  exit 0
}

trap cleanup SIGINT SIGTERM

# Start the containers using docker-compose
echo "🚀 Starting database and backend..."
docker-compose -f $ROOT/scripts/docker-compose.yaml up -d --build
echo "✅ Database and backend started."

# Tail the logs of each service separately
docker-compose -f $ROOT/scripts/docker-compose.yaml logs -f oracle > $ROOT/scripts/oracle.log &
docker-compose -f $ROOT/scripts/docker-compose.yaml logs -f backend > $ROOT/scripts/backend.log &

echo ""
echo "📊 Backend: http://localhost:8080"
echo "🗄️ Oracle DB: localhost:1521"
echo ""

tail -f $ROOT/scripts/oracle.log $ROOT/scripts/backend.log

# Sleep forever
while true; do
  sleep 1
done
