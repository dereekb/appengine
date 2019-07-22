#!/bin/sh
# Publishes to Artifactory
echo "Deploying to Artifactory."

# Jenkins Variables
branch=$GIT_BRANCH

cd "./appengine"

if [ $branch = "master" ]; then
echo "Release for master."
sh -c "./release-version.sh"
else
echo "Release for development."
fi;

sh -c "mvn deploy -DskipTests=true -Pdeploy"
