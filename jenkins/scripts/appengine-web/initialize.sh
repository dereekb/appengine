#!/bin/sh
echo "Initializing Angular/npm.";

cd "./appengine-web"

echo "Logging Into Repository";

# Set NPM Repository
##sh -c "npm set registry ${NPM_REGISTRY_URL}"
# Login To Repository
##sh -c "npm-cli-login -r ${NPM_REGISTRY_URL} -u ${VERDACCIO_LOGIN_USR} -p ${VERDACCIO_LOGIN_PSW} -e ${NPM_EMAIL_ADDRESS}"

echo "//dev.dereekb.com:10000/verdaccio//:_authToken=R+GtVHZeXKANECATWsZwxBdaYLvZru7g5JgIduDdLKCWqoodJ738DPI4WpT++/Ay" >> ~/.npmrc
echo "registry=https://dev.dereekb.com:10000/verdaccio/" >> ~/.npmrc

echo "Installing Node Modules";

echo "Root NPMRC";
sh -c "cat /root/.npmrc"

echo "Home NPMRC";
sh -c "cat ~/.npmrc"

# Install
sh -c "ls"

sh -c "npm install -dd"
