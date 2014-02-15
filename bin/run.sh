#!/bin/sh

export MONGODB_URI=mongodb://localhost:27017
export LANG=en
export COUNTRY=EN
export USER=user
export PASS=pass
export PORT=5000
export DB=fundsoverview

mvn jetty:run
