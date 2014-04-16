# Fundsoverview

Displays funds and their previous performance (not that it will matter concerning the performance in the future) on a dashboard to avoid checking them one by one. The stocks are accessed not via an API but by web-scraping, which makes the app itself very brittle. Sadly some funds are not listed in the yahoo finance api and therefore one has to make due with scraping for now.

## Libraries
The app uses Vaadin7 in a MVP pattern, mongoDB, Mockito, Powermock, Jsoup, mBassador EventBus, Guice DI

## Run
To create a war and the initial widgetset for the application:

    mvn package

Once package has been run you can run the project on the embedded Jetty server using:

    mvn jetty:run

This will start a Jetty server with the project at [http://localhost:8080/](http://localhost:8080/)

Also make sure the mongo daemon is running on port 27017 or edit the port accordingly in:

    ./bin/run.sh

## Deployment
In the following example we use the free services of [heroku](https://heroku.com) to deploy the application
### Preparations
You need to have [Heroku_Toolbelt](https://toolbelt.heroku.com) and Git installed

Create the app:

    heroku create

Deploy your code:

    git push heroku master

On the heroku dashboard add an Add-on to the App for the Database. In our case MongoHQ or MongoLab sandboxes. Get the Mongo URI that looks like this: `mongodb://<user>:<password>@paulo.mongohq.com:10037/<database_name>`

Invoke and follow the script to set the necessary environment variables on the heroku platform:

Note: The DB var is the same as the `<database_name>` part in the URI

    ./bin/set_heroku_env_vars.sh

## Used Environment Variables
The values are examples

    MONGODB_URI=mongodb://localhost:27017
    LANG=en
    COUNTRY=EN
    USER=user
    PASS=pass
    PORT=5000
    DB=fundsoverview
