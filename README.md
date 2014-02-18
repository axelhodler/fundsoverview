# Financegrabber

Work in progress

Displays funds and their previous performance (not that it will matter concerning the performance in the future) to avoid checking them one by one. The stocks are accessed not via an API but by web-scraping, which makes the app itself very brittle. Sadly some funds are not listed in the yahoo finance api and therefore one has to make due with scraping for now.

## Libraries
The app uses Vaadin7 in a MVP pattern, mongoDB, Mockito, Powermock, Jsoup, mBassador EventBus, Guice DI

## Run

To create a war and the initial widgetset for the application:
    mvn package

Once package has been run you can run the project on the embedded Jetty server using
    mvn jetty:run

This will start a Jetty server with the project at http://localhost:8080/

Also make sure the mongo daemon is running on port 27017 or edit the port accordingly in
    /bin/run.sh

### Used Environment Variables

*MONGODB_URI=mongodb://localhost:27017
*LANG=en
*COUNTRY=EN
*USER=user
*PASS=pass
*PORT=5000
*DB=fundsoverview
