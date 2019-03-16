#!/bin/sh
echo "Initializing Angular/npm.";

echo "Logging Into Repository";

# Set NPM Repository
##sh -c "npm set registry ${NPM_REGISTRY_URL}"
##sh -c "npm-cli-login -r ${NPM_REGISTRY_URL} -u ${VERDACCIO_LOGIN_USR} -p ${VERDACCIO_LOGIN_PSW} -e ${NPM_EMAIL_ADDRESS}"

# Login To Verdaccio Repository by writing to npmrc
echo "//${NPM_REGISTRY_URL}:_authToken=${NPM_TOKEN}" >> ~/.npmrc
echo "registry=https://${NPM_REGISTRY_URL}" >> ~/.npmrc

# Install
echo "Installing Node Modules";
cd "./appengine-web"
sh -c "npm install -dd"
