FROM openjdk:8-jdk-alpine

ADD target/projects-service.jar projects-service.jar

ENTRYPOINT ["java","-jar","/projects-service.jar"]

EXPOSE 8080