#!/bin/sh
# Publish To Verdaccio
set -e
private_registry="https://dev.dereekb.com:4873/verdaccio"

# Publish
sh -c "npm --registry $private_registry publish"
