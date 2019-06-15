#!/bin/sh
echo "Kill any processes that are declared below on port 8181. These should be java and the beta server: "
lsof -i :8181
