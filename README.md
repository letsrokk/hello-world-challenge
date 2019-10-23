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
2019-10-23 15:01:12,164 INFO  [io.sma.rea.mes.ext.MediatorManager] (main) Deployment done... start processing
2019-10-23 15:01:12,177 INFO  [io.sma.rea.mes.imp.ConfiguredChannelFactory] (main) Found incoming connectors: [smallrye-amqp]
2019-10-23 15:01:12,177 INFO  [io.sma.rea.mes.imp.ConfiguredChannelFactory] (main) Found outgoing connectors: [smallrye-amqp]
2019-10-23 15:01:12,177 INFO  [io.sma.rea.mes.imp.ConfiguredChannelFactory] (main) Stream manager initializing...
2019-10-23 15:01:12,178 INFO  [io.sma.rea.mes.ext.MediatorManager] (main) Initializing mediators
2019-10-23 15:01:12,179 INFO  [io.sma.rea.mes.ext.MediatorManager] (main) Connecting mediators
2019-10-23 15:01:12,182 INFO  [io.quarkus] (main) Quarkus 0.23.2 started in 1.809s. Listening on: http://0.0.0.0:8080
2019-10-23 15:01:12,184 INFO  [io.quarkus] (main) Profile prod activated. 
2019-10-23 15:01:12,184 INFO  [io.quarkus] (main) Installed features: [agroal, cdi, flyway, hibernate-orm, jdbc-h2, narayana-jta, resteasy, resteasy-jackson, smallrye-context-propagation, smallrye-openapi, smallrye-reactive-messaging, smallrye-reactive-messaging-amqp, smallrye-reactive-streams-operators, swagger-ui, vertx]
```
By default, application runs on port `8080`

## Documentation

Swagger UI is available on 
```
http://localhost:8080/challenge/swagger-ui/
```
![Swagger UI](readme/swagger-ui.png "Swagger UI")