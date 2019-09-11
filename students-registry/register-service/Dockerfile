FROM openjdk:8-jdk-alpine

ADD target/register-service.jar /app/
WORKDIR /app
ENTRYPOINT ["java","-jar","register-service.jar"]

EXPOSE 8080