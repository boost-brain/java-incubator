FROM openjdk:8-jdk-alpine

ADD target/users-service.jar /app/
WORKDIR /app

ENTRYPOINT ["java","-jar","users-service.jar"]

EXPOSE 8080