#!/bin/bash

./mvnw package
docker-compose build
