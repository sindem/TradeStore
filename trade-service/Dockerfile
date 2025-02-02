FROM openjdk:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} trade-service.jar
ENTRYPOINT java  -jar /trade-service.jar
