# Financegrabber

will later on grab a few stocks, listed in a config file, from the corresponding site and present it in a dashboard like form to avoid checking these stocks one by one.

the stocks are not listed in the yahoo finance api

will use jsoup and vaadin and probably deployed on heroku

## Run

To create a war and the initial widgetset for the application:
    mvn package

Once package has been run you can run the project on the embedded Jetty server using
    mvn jetty:run

This will start a Jetty server with the project at http://localhost:8080/

## Test

Currently using htmlunit, invoke via:

    mvn test

## Todo

* start jetty automatically when running "mvn test"