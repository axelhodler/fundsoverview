#!/bin/sh

export MONGODB_URI=mongodb://localhost:12345
export LANG=en
export COUNTRY=EN
export USER=user
export PASS=pass
export PORT=5000
export DB=fundsoverview

mvn test
