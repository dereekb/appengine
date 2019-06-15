#!/bin/sh

##
# https://angular.io/cli/test

# Can specify a specific project to run.
# Example: ./watch.sh @gae-web/appengine-client

# Tests in the browser
sh -c "ng test $@ --browsers=Chrome --karmaConfig=karma.browser.conf.js --codeCoverage=false --reporters"