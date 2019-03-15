#!/bin/sh
# Publish To Verdaccio
set -e

# Publish
sh -c "npm --registry ${NPM_REPOSITORY_URL} publish"
