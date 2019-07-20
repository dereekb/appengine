#!/bin/sh
# Publishes to Verdaccio

# Jenkins Variables
branch=$GIT_BRANCH
releaseId=${BUILD_NUMBER:?no build number was available in the environment}     # Comes from Jenkins

# Publish Each Project at projects path
cd ./appengine-web/projects/gae-web
for libName in *; do

  # Set the version number based on the buildId
  if [ $branch = "master" ]; then
  echo "Release for $libName for master. Using existing version number."
  else
  echo "Release for $libName for pre-release build. Using release number: $releaseId"
  sh -c "cd $libName && npm version prerelease --preid $releaseId"  # Set the Build Number First
  fi;

  # Publish
  sh -c "cd $libName && npm publish"
done
