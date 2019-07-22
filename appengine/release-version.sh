#!/bin/sh

echo "Removing snapshot of appengine java components for release."
sh -c "mvn versions:set -DremoveSnapshot=true"
