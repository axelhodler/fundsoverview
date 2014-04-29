#!/bin/sh

export LC_ALL="en_US.UTF-8"

export MONGODB_URI=test
export LANG=en
export COUNTRY=EN
export USER=user
export PASS=pass
export PORT=5000
export DB=test

mvn integration-test
