#!/bin/sh
# Publishes to Verdaccio

# Jenkins Variables
branch=$GIT_BRANCH
releaseId=${BUILD_NUMBER:?no build number was available in the environment}     # Comes from Jenkins

# Project Variables
projectsFolder="./appengine-web/projects/gae-web"
projects=( appengine-analytics appengine-api appengine-client appengine-components appengine-gateway appengine-services appengine-token appengine-utility )

# Publish Each Project at the specified path
for project in "${projects[@]}"
do
  # Set Our Directory
  cd $projectsFolder/$project

  # Set the version number based on the buildId
  if [ "$branch" = "master"]; then
  echo "Release for master. Using existing version number."
  else
  echo "Release for pre-release build. Using release number: $releaseId"
  sh -c "npm version prerelease --preid $releaseId"  # Set the Build Number First
  fi;

  # Publish
  sh -c "npm publish"
done
