FROM openjdk:8-jdk-alpine

ADD target/messages-service.jar /app/
WORKDIR /app

ENTRYPOINT ["java","-jar","messages-service.jar"]

EXPOSE 8080