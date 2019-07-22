#!/bin/sh

# Build SASS Files For Each Project
# The files are copied to their respective dist folders
sh -c "scss-bundle -e ./projects/gae-web/appengine-components/src/_styles.scss -d ./dist/gae-web/appengine-components/styles.scss"
sh -c "scss-bundle -e ./projects/gae-web/appengine-gateway/src/_styles.scss -d ./dist/gae-web/appengine-gateway/styles.scss"
