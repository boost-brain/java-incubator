FROM openjdk:8-jdk-alpine

ADD target/proxy.jar /app/
WORKDIR /app

ENTRYPOINT ["java","-jar","proxy.jar"]

EXPOSE 9000