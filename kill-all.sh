#!/usr/bin/env bash

# This script will kill the client process on 4200, the server on 8080 and the postgresql
# instance running in the docker container.

docker-compose down

kill_process_on_port() {
  local PORT=$1
  
  echo "Looking for process on port $PORT..."
  
  PID=$(sudo ss -lptn "sport = :$PORT" | awk '/LISTEN/ {split($NF, a, ","); sub("pid=", "", a[2]); print a[2]}')
  
  if [[ "$PID" =~ ^[0-9]+$ ]]; then
    echo "Process found on port $PORT with PID: $PID"
    
    echo "Killing process $PID..."
    sudo kill -9 $PID
    
    if [ $? -eq 0 ]; then
      echo "Process $PID killed successfully."
    else
      echo "Failed to kill process $PID."
    fi
  else
    echo "No valid process found running on port $PORT."
  fi
}

kill_process_on_port 8080
kill_process_on_port 4200
