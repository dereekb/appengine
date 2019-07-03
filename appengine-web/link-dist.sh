#!/bin/sh
# Links the dist folders to the local install.

cd ./dist/gae-web

for libName in *; do
  # Link Each Library
  sh -c "cd $libName && npm link"
done
