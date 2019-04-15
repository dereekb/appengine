#!/bin/sh


sh -c "gcloud beta emulators datastore start --quiet --no-store-on-disk & mvn appengine:devserver -Dmaven.javadoc.skip=true -DskipTests=true"
