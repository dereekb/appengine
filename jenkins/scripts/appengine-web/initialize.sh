#!/bin/sh
echo "Installing Node Modules";
cd "./appengine-web"
sh -c "npm install --loglevel info"
