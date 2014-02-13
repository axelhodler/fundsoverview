#!/bin/sh

export MONGODB_URI=mongodb://localhost:27017
export LANG=en
export COUNTRY=EN
export USER=user
export PASS=pass

mvn jetty:run
