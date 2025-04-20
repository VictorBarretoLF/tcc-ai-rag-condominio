#!/bin/bash

docker compose -f ../postgres-database/docker-compose.yml up -d --build postgres

cd ..

./gradlew clean build