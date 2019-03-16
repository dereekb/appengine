#!/bin/sh
echo "Initializing Angular/npm.";

# Set NPM Repository
sh -c "npm set registry ${NPM_REGISTRY_URL}"

# Login To Repository
sh -c "npm-cli-login -r ${NPM_REGISTRY_URL} -u ${VERDACCIO_LOGIN_USR} -p ${VERDACCIO_LOGIN_PSW} -e ${NPM_EMAIL_ADDRESS}"

sh -c "echo ~/.npmrc"

# Install
cd "./appengine-web"
sh -c "npm install"
