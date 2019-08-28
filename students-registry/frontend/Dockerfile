FROM openjdk:8-jdk-alpine

COPY output/*.jar frontend.jar

ENTRYPOINT ["java","-jar","/frontend.jar"]

EXPOSE 80 80