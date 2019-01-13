#!/bin/bash

# Rebuild Docker
docker-compose -f docker-compose.yml -f docker-compose-nginx.yml build #--no-cache
