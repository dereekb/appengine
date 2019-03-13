#!/bin/bash
mvn -f appengine install -DskipTests
mvn -f appengine -B -DskipTests clean package
