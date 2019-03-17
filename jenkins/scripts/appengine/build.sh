#!/bin/sh
cd "./appengine"
sh -c "mvn -B -DskipTests clean package"
