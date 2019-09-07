FROM openjdk:8-jdk-alpine

COPY target/*.jar jsf-frontend.jar

ENTRYPOINT ["java","-jar","/jsf-frontend.jar"]

EXPOSE 8080