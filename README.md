# Diorasi Analytics

## Installtion Guide

1. Clone repository from github

1. Set up db. Check if you have a Postgres installed.
    a. If yes then stop it by using the command:
    ``` sudo pkill -u postgres ```
    b. Then start the container with the specific postgres docker image
    ```
    cd /home/sofia/workspace/diorasi-analytics
    docker-compose up
    ```
    c. And finally create the database and the tables
    ```
    ./gradlew -Dflyway.configFiles=/home/sofia/workspace/diorasi-analytics/core/src/main/resources/flyway.conf flywayMigrate
    ```

1. Java 11

1. Open eclispe and Import Gradle project

1. 


## Execute db migrations
```
./gradlew -Dflyway.configFiles=/home/sofia/workspace/diorasi-analytics/core/src/main/resources/flyway.conf flywayMigrate
```


## TEST

In order to run the integration tests you have to create a new test database called: **test-diorasi**. Depending if you use docker for postgres or not the procedure is different.

### Create test db
```
./gradlew -Dflyway.configFiles=/home/sofia/workspace/diorasi-analytics/core/src/test/resources/flyway-test.conf flywayMigrate
```

### Clear db
```
./gradlew -Dflyway.configFiles=/home/sofia/workspace/diorasi-analytics/core/src/test/resources/flyway-test.conf flywayClean
```

### Run tests
- ./gradlew test
