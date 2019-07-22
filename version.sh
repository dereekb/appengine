#!/bin/sh
version=${1:?version is required}
isRelease=${2:-false}

echo "Updating version of project components to $version."

sh -c "cd appengine && ./version.sh $version $isRelease"
sh -c "cd appengine-web && ./version.sh $version"
