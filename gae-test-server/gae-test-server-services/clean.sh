#!/bin/bash

# Remove the temporary directory.
rm -r .tmp

# Remove the containers
docker-compose -f docker-compose.yml -f docker-compose-nginx.yml down
