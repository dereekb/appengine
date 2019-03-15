#!/bin/sh
echo "Initializing Maven.";

# Copy Maven settings to the repo
cp ./jenkins/maven/settings.xml /root/.m2/settings.xml
