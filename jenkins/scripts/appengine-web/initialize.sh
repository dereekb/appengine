#!/bin/sh
echo "Initializing Angular/npm.";

webAppDirectory="appengine-web"

# Installing
sh -c "npm install --prefix ${webAppDirectory}"
