#!/bin/bash

docker-compose -f docker-compose.yml -f docker-compose-nginx.yml build --no-cache sos-dev-server
