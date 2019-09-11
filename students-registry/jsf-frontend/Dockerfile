FROM openjdk:8-jdk-alpine

ADD target/jsf-frontend.jar /app/
WORKDIR /app

ENTRYPOINT ["java","-jar","jsf-frontend.jar"]

EXPOSE 8080