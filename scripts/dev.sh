#!/bin/bash
set -e
cd `dirname $0`/..
ROOT=`pwd`

cleanup() {
  echo -e "\r🧹Cleaning up..."
  pkill -P $$ || true
  docker-compose -f $ROOT/scripts/docker-compose.yaml down --volumes --remove-orphans 2>&1 > /dev/null || true
  echo "Done."
  exit 0
}

trap cleanup SIGINT SIGTERM EXIT


# Setting environment variables
export ORACLE_PASSWORD=cinema
export APP_USER=cinema
export APP_USER_PASSWORD=cinema

export CINEMA_JASYPT_KEY=cinema

export JWT_SECRET_KEY=hzwg44y7qN0DPwsj2PnNlonXSXApDIe/85Tzjh+eM98=
export JWT_ACCESS_TOKEN_EXPIRATION_MS=600000
export JWT_REFRESH_TOKEN_EXPIRATION_MS=86400000

export SPRING_DATASOURCE_URL=jdbc:oracle:thin:@oracle:1521/FREEPDB1
export SPRING_DATASOURCE_USERNAME=${APP_USER}
export SPRING_DATASOURCE_PASSWORD=${APP_USER_PASSWORD}

export SERVER_HOST=localhost
export SERVER_PORT=8080

export SPRING_SQL_INIT_MODE=always
export SPRING_SQL_INIT_PLATFORM=oracle
export SPRING_SQL_INIT_SCHEMA_LOCATIONS=classpath:db/migration/V1__init.sql
export SPRING_SQL_INIT_DATA_LOCATIONS=classpath:db/data.sql

# Start the containers using docker-compose
echo "Building backend..."
./gradlew bootJar
echo "Done."

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

# Follow logs to stdout
tail -f "$ROOT/scripts/oracle.log" "$ROOT/scripts/backend.log" &
TAIL_PID=$!

# Monitor container status
while true; do
  sleep 3

  # Check if any of the key containers exited
  if ! docker ps --filter "name=oracle" --filter "status=running" | grep oracle > /dev/null; then
    echo "❌ Oracle container exited."
    break
  fi
  if ! docker ps --filter "name=backend" --filter "status=running" | grep backend > /dev/null; then
    echo "❌ Backend container exited."
    break
  fi
done

# spring container 종료됨 → docker-compose도 종료
kill $COMPOSE_PID              # docker-compose up 프로세스 강제 종료
wait $COMPOSE_PID              # 종료까지 대기 → EXIT → cleanup 실행

kill $TAIL_PID 2>/dev/null || true
wait $TAIL_PID 2>/dev/null || true