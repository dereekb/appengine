#!/bin/sh
version=${1:?version is required}
echo "Updating version of all gae-web libraries to $version..."

# Package.json
echo "Attempting to root project version to $version"
sh -c "npm version $version"

# Libraries
cd ./projects/gae-web

for libName in *; do
  # Set Version 
  echo "Attempting to update version of $libName to $version"
  sh -c "cd $libName && npm version $version"
done
