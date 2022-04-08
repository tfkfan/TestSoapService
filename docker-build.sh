#!/bin/bash
./mvnw clean package -Pprod verify jib:dockerBuild
