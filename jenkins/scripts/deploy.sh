#!/usr/bin/env bash

# Deploy to artifactory
# 
echo "Deploying to Artifactory."
mvn deploy -Dbuildnumber=${buildnumber} -DskipTests=true
