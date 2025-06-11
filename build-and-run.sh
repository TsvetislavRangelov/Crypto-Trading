#!/usr/bin/env bash

# This script will build and run both the postgresql database in the container, the client and the server.

echo "Starting build and run."

docker-compose up -d

cd crypto-server/

./gradlew build
nohup ./gradlew bootRun &

cd ../crypto-client/

npm install
nohup ng serve &

