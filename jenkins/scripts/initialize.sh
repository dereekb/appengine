#!/usr/bin/env bash
echo "Initializing Maven.";

# Copy Maven settings to the repo
cp ./jenkins/maven/settings.xml /root/.m2/settings.xml

# Install Dependencies
mvn install
