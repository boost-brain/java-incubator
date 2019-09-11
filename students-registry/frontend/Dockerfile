FROM openjdk:8-jdk-alpine

ADD target/frontend.jar /app/
WORKDIR /app

ENTRYPOINT ["java","-jar","/frontend.jar"]

EXPOSE 8080 8080