#!/bin/sh
echo "Deploying to Artifactory."
mvn -f appengine deploy -DskipTests=true
