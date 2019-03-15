#!/bin/sh
echo "Initializing Angular/npm.";

# Set NPM Repository
sh -c "npm config set registry ${NPM_REPOSITORY_URL}"

# Install
cd "./appengine-web"
sh -c "npm install"
