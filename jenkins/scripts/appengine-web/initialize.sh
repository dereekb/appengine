#!/bin/sh
echo "Initializing Angular/npm.";

# Set NPM Repository
echo "//${NPM_REGISTRY_AUTH_URL}:_authToken=${NPM_TOKEN}" > .npmrc
sh -c "npm config set registry ${NPM_REGISTRY_URL}"

# Print NPMRC For Testing
cat .npmrc

# Install
cd "./appengine-web"
sh -c "npm install"
