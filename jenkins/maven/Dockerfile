FROM maven:3-alpine

# gcloud
RUN apk add --update curl python bash &&  rm -rf /var/cache/apk/*
ENV HOME /
#RUN curl https://sdk.cloud.google.com | bash
ENV GCLOUD_PROJECT=gae-web-app-237603
ENV DATASTORE_EMULATOR_HOST=0.0.0.0:8181
ARG CLOUD_SDK_VERSION=241.0.0
ARG SHA256SUM=ee5658a08983de18bc061368af1785783e66d433602fd01eee7934e4343bfa5e
RUN curl -O https://dl.google.com/dl/cloudsdk/channels/rapid/downloads/google-cloud-sdk-${CLOUD_SDK_VERSION}-linux-x86_64.tar.gz
RUN echo "${SHA256SUM}  google-cloud-sdk-${CLOUD_SDK_VERSION}-linux-x86_64.tar.gz" > google-cloud-sdk-${CLOUD_SDK_VERSION}-linux-x86_64.tar.gz.sha256
RUN sha256sum -c google-cloud-sdk-${CLOUD_SDK_VERSION}-linux-x86_64.tar.gz.sha256
RUN tar xzf google-cloud-sdk-${CLOUD_SDK_VERSION}-linux-x86_64.tar.gz 
ENV PATH /google-cloud-sdk/bin:$PATH
RUN gcloud config set core/disable_usage_reporting true
RUN gcloud config set component_manager/disable_update_check true
RUN gcloud config set project ${GCLOUD_PROJECT}
VOLUME ["/.config"]

# Maven
## Maven Environment Variables
ENV ARTIFACTORY_CONTEXT_URL="https://dev.dereekb.com:10000/artifactory"

# Required login info
ENV MAVEN_CREDS_USR=
ENV MAVEN_CREDS_PSW=

# Copy settings.xml
ARG SETTINGS_FOLDER_PATH=./jenkins/maven
COPY $SETTINGS_FOLDER_PATH/settings.xml /root/.m2/settings.xml
