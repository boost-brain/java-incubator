FROM openjdk:8-jdk-alpine

COPY target/*.jar frontend-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/frontend-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080