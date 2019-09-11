FROM openjdk:8-jdk-alpine

ADD target/tasks-service.jar /app/
WORKDIR /app

ENTRYPOINT ["java","-jar","tasks-service.jar"]

EXPOSE 8080