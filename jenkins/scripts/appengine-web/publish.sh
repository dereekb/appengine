#!/bin/sh

# Publish
PROJECTS="./appengine-web/projects/gae-web/appengine-client"
# PROJECTS="${PROJECTS} ./appengine-web/projects/gae-web/appengine-client"

# Publish Each Project at the specified path
for PROJECT in ${PROJECTS}; do
  cd ${PROJECT}
  # sh -c "npm patch"               # TODO: "Patch" a version forward, unless we're on master.
  # sh -c "npm unpublish --force"   # Unpublish before publishing.
  sh -c "npm publish"               # Publish
done
