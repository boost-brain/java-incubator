FROM openjdk:8

ADD target/example-service.jar /app/
WORKDIR /app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "example-service.jar"]