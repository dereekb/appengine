#!/bin/sh
sh -c "mvn appengine:devserver -Dmaven.javadoc.skip=true -DskipTests=true"
