#!/bin/bash

# Initialize
mkdir .tmp  # Create the .tmp directory

# Start Docker
docker-compose -f docker-compose.yml -f docker-compose-nginx.yml up
