#!/bin/sh
# Unlinks the dist folders from the local install.

cd ./dist/gae-web

for libName in *; do
  # Unlink Each Library
  sh -c "cd $libName && npm unlink"
done
