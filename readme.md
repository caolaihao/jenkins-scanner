# Introduction

To show jenkins build success rate in Grafana, this project will gather jenkins build data and insert into a database.

This project also demonstrate how to implement TDD practice in a real project, which depends on DB and external system(Jenkins). 

The project will:
1. Get job build info from Jenkins incrementally.
2. Save Jenkins build data into DB(MySQL)


DB Schema:
Job, buildNumber, buildTime, status, trigger

## BUILD

```
./gradlew clean build
```

## RUN

Using Jenkins & MySQL settings stored in application.properties:

```
java -jar build/libs/jenkins-0.0.1-SNAPSHOT.jar
```

Using customized Jenkins & MySQL settings through arguments:

```
java -jar build/libs/jenkins-0.0.1-SNAPSHOT.jar \
    --spring.datasource.url=jdbc:mysql://localhost:3306/jenkinsdata \
    --spring.datasource.username=jenkinsdata \
    --spring.datasource.password=jenkinsdata \
    --jenkins.url=http://localhost:8080/jenkins \
    --jenkins.user=xxx \
    --jenkins.password=xxxxxx
    
```

## TASKING

Here are tasks:

    1. get job list
    2. get job build list by job
    3. get new build list after a certain build number. 
    4. get job build detail by build number
    5. save build detail into DB  
    6. if scan failed, then should exit with non-zero code
    7. get last build number from DB by job name
    8. integration: 
        given: job name
        when: update build info
        then: 
            * get last build nunmber
            * get new build list after the number
            * save every build detail into DB