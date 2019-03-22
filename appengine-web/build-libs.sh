#!/bin/sh

cd projects/gae-web

for libName in *; do
    # Build each library.
    sh -c "ng build @gae-web/$libName"
done
