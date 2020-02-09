FROM openjdk:8-jdk-alpine

COPY target/*.jar tasks-service-1.0-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/tasks-service-1.0-SNAPSHOT.jar"]

EXPOSE 8080