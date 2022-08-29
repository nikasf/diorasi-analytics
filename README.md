# Diorasi Analytics

## Execute db migrations
```
./gradlew -Dflyway.configFiles=/home/sn/workspace/diorasi/core/src/main/resources/flyway.conf flywayMigrate
```


## TEST

### Create test db
```
./gradlew -Dflyway.configFiles=/home/sn/workspace/diorasi/core/src/test/resources/flyway-test.conf flywayMigrate
```

### Clear db
```
./gradlew -Dflyway.configFiles=/home/sn/workspace/diorasi/core/src/test/resources/flyway-test.conf flywayClean
```

### Run tests
- ./gradlew test
