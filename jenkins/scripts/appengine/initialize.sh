#!/bin/sh

# Install
cd "./appengine"
sh -c "mvn install -DskipTests=true"
