#!/bin/sh
version=${1:?version is required}
isRelease=${2:-false}

echo "Updating version of appengine java components to $version."
sh -c "mvn versions:set -DnewVersion=$version-SNAPSHOT -DprocessAllModules=true -DremoveSnapshot=$isRelease"
