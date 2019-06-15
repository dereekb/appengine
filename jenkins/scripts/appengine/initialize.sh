#!/bin/sh

# Install
sh -c "env"   # Temp

cd "./appengine"
sh -c "mvn install -DskipTests=true"
