#!/bin/sh

# Install node_modules if necessary.
echo "Installing Dependencies"
sh -c "npm install --loglevel info"

# Run the test server
echo "Starting Test Server"
sh -c "ng serve --proxy-config"
