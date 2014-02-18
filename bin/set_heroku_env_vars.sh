#!/bin/bash

echo "Set the values of the needed environment variables, don't forget to doublecheck your input"

echo "Set value for MONGODB_URI"
read uri
heroku config:set MONGODB_URI=$uri

echo "Set value for the language (LANG)"
read lang
heroku config:set LANG=$lang

echo "Set value for the COUNTRY"
read country
heroku config:set COUNTRY=$country

echo "Set value for the USER"
read user
heroku config:set USER=$user

echo "Set value for the password (PASS)"
read pass
heroku config:set PASS=$pass

echo "Set value for the databasename (DB)"
read db
heroku config:set DB=$db
