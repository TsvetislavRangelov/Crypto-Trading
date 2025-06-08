#!/usr/bin/env bash

docker-compose down

# Function to kill a process on a given port
kill_process_on_port() {
  local PORT=$1
  
  echo "Looking for process on port $PORT..."
  
  # Get the PID of the process using the port
  PID=$(sudo ss -lptn "sport = :$PORT" | awk '/LISTEN/ {split($NF, a, ","); sub("pid=", "", a[2]); print a[2]}')
  
  if [[ "$PID" =~ ^[0-9]+$ ]]; then
    echo "Process found on port $PORT with PID: $PID"
    
    # Kill the process
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

# Kill processes on ports 8080 (Spring Boot) and 4200 (Angular)
kill_process_on_port 8080
kill_process_on_port 4200
