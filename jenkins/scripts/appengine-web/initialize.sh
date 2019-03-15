#!/bin/sh
echo "Initializing Angular/npm.";

# Login To Repository
echo ${VERDACCIO_LOGIN_USR}
echo ${VERDACCIO_LOGIN_PWD}
echo ${NPM_REGISTRY_URL}
sh -c "npm-cli-login -r ${NPM_REGISTRY_URL} -u ${VERDACCIO_LOGIN_USR} -p ${VERDACCIO_LOGIN_PWD} -e ${NPM_EMAIL_ADDRESS}"

# Set NPM Repository
sh -c "npm config set registry ${NPM_REGISTRY_URL}"

# Install
cd "./appengine-web"
sh -c "npm install"
