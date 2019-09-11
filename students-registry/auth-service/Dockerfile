FROM openjdk:8-jdk-alpine

ADD target/auth-service.jar /app/
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "auth-service.jar"]