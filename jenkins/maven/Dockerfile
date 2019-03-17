FROM maven:3-alpine

## Maven Environment Variables
ENV ARTIFACTORY_CONTEXT_URL="https://dev.dereekb.com:10000/artifactory"

# Required login info
ENV MAVEN_CREDS_USR=
ENV MAVEN_CREDS_PSW=

# Copy Settings
COPY ./jenkins/maven/settings.xml /root/.m2/settings.xml
