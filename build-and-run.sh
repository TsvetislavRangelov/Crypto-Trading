#!/usr/bin/env bash

echo "Starting build and run."

docker-compose up -d

cd crypto-server/

./gradlew build
nohup ./gradlew bootRun &

cd ../crypto-client/

npm install
nohup ng serve &

