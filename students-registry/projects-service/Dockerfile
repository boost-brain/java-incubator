FROM openjdk:8-jdk-alpine

COPY target/*.jar projects-service.jar

ENTRYPOINT ["java","-jar","/projects-service.jar"]

EXPOSE 8080