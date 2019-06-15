#!/bin/sh
echo "Deploying to Artifactory."
cd "./appengine"
sh -c "mvn deploy -DskipTests=true"
