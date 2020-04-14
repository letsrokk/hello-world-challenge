# hello-world-challenge

Simple Java application built with [Quarkus](https://quarkus.io) framework. Used as a sample application for "challenge" (test) task for candidates on 
Test Automation Engineer position 

## Story and Challenge Task

> **As a** user  
> **I want to** send Hello request  
> **So that** I am greeted by the app in response

We need to create test automation project and cover this story with set of automated tests (minimum of 1 positive 
and 1 negative scenario, but the more the merrier).

## Run with Docker

```
% docker run -p 8080:8080 -d letsrokk/hello-world-challenge:latest
```

## Run with Java

```
% java -jar hello-world-challenge-runner.jar 
```
```
2019-10-23 15:12:33,730 INFO  [io.quarkus] (main) Quarkus 0.23.2 started in 1.640s. Listening on: http://0.0.0.0:8080
2019-10-23 15:12:33,737 INFO  [io.quarkus] (main) Profile prod activated. 
2019-10-23 15:12:33,738 INFO  [io.quarkus] (main) Installed features: [agroal, cdi, hibernate-orm, jdbc-h2, narayana-jta, resteasy, resteasy-jackson, smallrye-openapi, swagger-ui]
```
By default, application runs on port `8080`

Latest version of JAR-file can be found in [Releases](https://github.com/letsrokk/hello-world-challenge/releases/latest) on GitHub

## Documentation

Swagger UI is available on 
```
http://localhost:8080/challenge/swagger-ui/
```
![Swagger UI](readme/swagger-ui.png "Swagger UI")
