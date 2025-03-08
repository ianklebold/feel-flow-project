# Dockerfile
# FROM openjdk:21-jdk-slim
# VOLUME /tmp
# COPY target/feel-flow-package-0.0.1-SNAPSHOT.jar app.jar
# ENTRYPOINT ["java","-jar","/app.jar"]

FROM eclipse-temurin:21-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]