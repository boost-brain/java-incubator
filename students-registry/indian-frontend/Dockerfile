FROM openjdk:8-jdk-alpine

ADD target/indian-frontend.jar /app/
WORKDIR /app

ENTRYPOINT ["java","-jar","indian-frontend.jar"]

EXPOSE 8080