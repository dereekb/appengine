#!/bin/sh
# Publish To Verdaccio
set -e
private_registry=${NPM_REPOSITORY_URL}

# Publish
sh -c "npm --registry $private_registry publish"
