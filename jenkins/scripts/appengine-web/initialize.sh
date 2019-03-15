#!/bin/sh
echo "Initializing Angular/npm.";

webAppDirectory="appengine-web"

# Set NPM Repository
sh -c "npm config set registry ${NPM_REPOSITORY_URL}"

# Install
sh -c "npm install --prefix ${webAppDirectory}"
