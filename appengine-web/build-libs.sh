#!/bin/sh

# Build in the specific order.
sh -c "ng build @gae-web/appengine-utility"
sh -c "ng build @gae-web/appengine-token"
sh -c "ng build @gae-web/appengine-api"
sh -c "ng build @gae-web/appengine-client"
sh -c "ng build @gae-web/appengine-analytics"
sh -c "ng build @gae-web/appengine-services"
sh -c "ng build @gae-web/appengine-components"
sh -c "ng build @gae-web/appengine-gateway"
