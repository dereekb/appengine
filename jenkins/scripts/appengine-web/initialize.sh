#!/bin/sh
echo "Initializing Angular/npm.";

# Install
echo "Installing Node Modules";
cd "./appengine-web"
sh -c "npm install -dd"
