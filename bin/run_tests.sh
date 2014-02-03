#!/bin/sh

export MONGODB_URI=mongodb://localhost:12345
export LANG=en
export COUNTRY=EN

mvn test
