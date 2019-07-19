FROM node:12-alpine

## Install Chromium for running tests via Karma
# https://github.com/karma-runner/karma-chrome-launcher/issues/189
# Installs latest Chromium package
RUN apk update && apk upgrade && \
    echo @edge http://nl.alpinelinux.org/alpine/edge/community >> /etc/apk/repositories && \
    echo @edge http://nl.alpinelinux.org/alpine/edge/main >> /etc/apk/repositories && \
    apk add --no-cache bash chromium@edge nss@edge

# This line is to tell karma-chrome-launcher where
# chromium was downloaded and installed to.
ENV CHROME_BIN /usr/bin/chromium-browser

# Tell Puppeteer to skip installing Chrome.
# We'll be using the installed package instead.
ENV PUPPETEER_SKIP_CHROMIUM_DOWNLOAD true

# Telling node-sass which pre-built binary to fetch.
# This way we don't need rebuilding node-sass each time!
ENV SASS_BINARY_NAME=linux-x64-67

## NPM Configuration and Initialization
# Args
ARG NPM_TOKEN
ARG NPM_REGISTRY_URL=dev.dereekb.com:10000/verdaccio/

# Login To Verdaccio Repository by writing to .npmrc
RUN echo "//$NPM_REGISTRY_URL:_authToken=$NPM_TOKEN" >> ~/.npmrc
RUN echo "registry=https://$NPM_REGISTRY_URL" >> ~/.npmrc

## Install NPM Dependencies (Build Python)
RUN apk --no-cache --update upgrade musl
RUN apk add --no-cache make gcc g++ python && \
  npm install -g --unsafe-perm node-gyp@^3.8.0 node-sass@4.12.0 scss-bundle typescript@3.4.2 @angular/cli@8.1.0 --loglevel info --production 
  ##&& \ apk del --force-broken-world make gcc g++

##python
