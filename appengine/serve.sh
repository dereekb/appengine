#!/bin/sh
DATASTORE_EMULATOR_HOST=${DATASTORE_EMULATOR_HOST:-"0.0.0.0:8181"}
HOST_PORT=${1:-${DATASTORE_EMULATOR_HOST}}
sh -c "gcloud --quiet beta emulators datastore start --host-port ${HOST_PORT} --no-store-on-disk & mvn appengine:devserver -Dmaven.javadoc.skip=true -DskipTests=true"
