#!/bin/bash
./mvnw clean package -Pprod verify jib:dockerBuild && docker-compose -f src/main/docker/app.yml up
