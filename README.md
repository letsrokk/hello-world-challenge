# hello-world-challenge

## Story

> **As a** user  
> **I want to** send Hello request  
> **So that** I am greeted by the app in response

## Run with Docker

TBD

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

## Documentation

Swagger UI is available on 
```
http://localhost:8080/challenge/swagger-ui/
```
![Swagger UI](readme/swagger-ui.png "Swagger UI")