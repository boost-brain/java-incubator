FROM openjdk:8-jdk-alpine

COPY target/*.jar users-service.jar

ENTRYPOINT ["java","-jar","/users-service.jar"]

EXPOSE 8080